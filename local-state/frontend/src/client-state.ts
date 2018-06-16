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
  draftRatings: []
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
      return cache.readFragment({ fragment, id });
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
      const newDraftRating = {
        __typename: "DraftRating",
        id: beerId,
        author,
        comment
      };

      cache.writeFragment({
        data: newDraftRating,
        fragment: gql`
          fragment draftRating on DraftRating {
            id
            author
            comment
          }
        `,
        id: `DraftRating:${beerId}`
      });

      return newDraftRating;
    }
  }
};

export default {
  typeDefs,
  defaults,
  resolvers
};
