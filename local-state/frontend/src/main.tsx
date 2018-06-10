import * as React from "react";
import * as ReactDOM from "react-dom";

import ApolloClient, { gql } from "apollo-boost";
import { ApolloProvider } from "react-apollo";

import BeerRatingApp from "./BeerRatingApp";
import { ApolloCache } from "apollo-cache";

// Point GraphQL Client
// https://github.com/apollographql/apollo-link-state/pull/180
// https://github.com/apollographql/apollo-link-state/blob/ff90610d311a8ebc9cc8945b0c00a12c4ce55aed/packages/apollo-link-state/src/__tests__/initialization.ts#L27
const typeDefs = `
    type NetworkStatus {
      isConnected: Boolean!
    }

    type DraftRating {
      # Must match beerId in Beer
      id: ID!
      author: String!
      comment: String!
    }

    type Query {
      networkStatus: NetworkStatus
      draftRatings: [DraftRating]!
      draftRatingForBeer(beerId: ID!): DraftRating
    }

    type Mutation {
      setDraftRatingForBeer(beerId: ID!, author: String!, comment: String!): DraftRating!
    }
  `;
// https://github.com/apollographql/apollo-client-devtools/issues
const client = new ApolloClient({
  uri: "http://localhost:9000/graphql",
  clientState: {
    defaults: {
      networkStatus: {
        __typename: "NetworkStatus",
        isConnected: true
      },
      draftRatings: [
        {
          __typename: "DraftRating",
          id: "B1",
          author: "Klaus-Dieter",
          comment: "Not bad at all"
        },
        {
          __typename: "DraftRating",
          id: "B2",
          author: "Nils",
          comment: "So-la-la"
        }
      ]
    },
    resolvers: {
      Query: {
        draftRatingForBeer: (_: any, args: any, { cache }: { cache: ApolloCache<any> }) => {
          console.log("localRatingForBeer", args.beerId);
          const id = `DraftRating:${args.beerId}`;

          const fragment = gql`
            fragment draftRating on DraftRating {
              author
              comment
            }
          `;
          const draftRating = cache.readFragment({ fragment, id });
          console.log("DRAFT RATING", draftRating);
          return draftRating;

          // return {
          //   __typename: "LocalRating",
          //   id: "LR333_" + args.beerId,
          //   author: "me"
          // };
        }
      },
      // setDraftRatingForBeer(beerId: ID!, author: String!, comment: String!): DraftRating!
      Mutation: {
        setDraftRatingForBeer: (
          _: any,
          { beerId, author, comment }: { beerId: string; author: string; comment: string },
          { cache }: { cache: ApolloCache<any> }
        ) => {
          console.log(`Writing draftRating for beer '${beerId}', author: '${author}', comment: '${comment}'`);
          const draftBefore = cache.readQuery({
            query: gql`
              query {
                draftRatings @client {
                  id
                  comment
                  author
                }
              }
            `
          });

          console.log("DraftRatings BEFORE", draftBefore);

          const cacheId = `DraftRating:${beerId}`;

          const draftRating = {
            __typename: "DraftRating",
            id: beerId,
            author,
            comment
          };

          console.log("draftRating", draftRating);

          const fragment = gql`
            fragment draftRating on DraftRating {
              id
              author
              comment
            }
          `;

          cache.writeFragment({
            fragment,
            data: draftRating,
            id: cacheId
          });

          const x = cache.readQuery({
            query: gql`
              query {
                draftRatings @client {
                  id
                  comment
                  author
                }
              }
            `
          });

          console.log("DraftRatings AFTER", x);

          return draftRating;
        }
      }
    },
    typeDefs: typeDefs
  }
});

const theBeerRatingApp = (
  <ApolloProvider client={client}>
    <BeerRatingApp />
  </ApolloProvider>
);

const mountNode = document.getElementById("app");
ReactDOM.render(theBeerRatingApp, mountNode);
