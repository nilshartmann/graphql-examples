import { ApolloCache } from "apollo-cache";
import gql from "graphql-tag";

const typeDefs = `
    type DraftRating {
      # Must match beerId in Beer
      id: ID!
      author: String!
      comment: String!
    }

    type Query {
      draftRatings: [DraftRating]!
      draftRatingForBeer(beerId: ID!): DraftRating

      currentBeerId: ID!
    }

    type Mutation {
      setDraftRatingForBeer(beerId: ID!, author: String!, comment: String!): DraftRating!

      setCurrentBeerId(beerId: ID!): ID!
    }
  `;

const defaults = {
  currentBeerId: "B1",
  draftRatings: [
    {
      __typename: "DraftRating",
      id: "B1",
      author: "Klaus-Dieter",
      comment: "Not bad at all"
    }
    // {
    //   __typename: "DraftRating",
    //   id: "B2",
    //   author: "Nils",
    //   comment: "So-la-la"
    // }
  ]
};

const resolvers = {
  Query: {
    draftRatingForBeer: (_: any, args: any, { cache }: { cache: ApolloCache<any> }) => {
      const id = `DraftRating:${args.beerId}`;

      const fragment = gql`
        fragment draftRating on DraftRating {
          id
          author
          comment
        }
      `;
      const rating = cache.readFragment({ fragment, id });
      console.log(`draftRatingForBeer Resolver for beerId '${args.beerId}' returned `, rating);
      return rating;
    }
  },
  Mutation: {
    setCurrentBeerId: (_: any, { beerId }: { beerId: string }, { cache }: { cache: ApolloCache<any> }) => {
      cache.writeData({ data: { currentBeerId: beerId } });
      return beerId;
    },
    setDraftRatingForBeer: (
      _: any,
      { beerId, author, comment }: { beerId: string; author: string; comment: string },
      { cache }: { cache: ApolloCache<any> }
    ) => {
      console.log(`Writing draftRating for beer '${beerId}', author: '${author}', comment: '${comment}'`);

      interface DraftRating {
        id: string;
        author: string;
        comment: string;
      }

      const query = gql`
        query GetDraftRatings {
          draftRatings @client {
            id
            author
            comment
          }
        }
      `;
      const previous = cache.readQuery({ query }) as { draftRatings: DraftRating[] };
      console.log("previous", previous);

      let found = false;
      const newDraftRating = {
        __typename: "DraftRating",
        id: beerId,
        author,
        comment
      };

      const newDraftRatings = previous.draftRatings.map(prevDraft => {
        if (prevDraft.id === beerId) {
          found = true;
          return newDraftRating;
        }

        return prevDraft;
      });

      if (!found) {
        newDraftRatings.push(newDraftRating);
      }
      const data = {
        draftRatings: newDraftRatings
      };

      console.log(data);
      console.log("======");

      cache.writeData({ data });
      return newDraftRating;

      // const fragment = gql`
      //   fragment draftRating on DraftRating {
      //     id
      //     author
      //     comment
      //   }
      // `;

      // cache.writeFragment({
      //   fragment,
      //   data: draftRating,
      //   id: `DraftRating:${beerId}`
      // });

      // return draftRating;
    }
  }
};

export default {
  typeDefs,
  defaults,
  resolvers
};
