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
    },
    {
      __typename: "DraftRating",
      id: "B2",
      author: "Nils",
      comment: "So-la-la"
    }
  ]
};

const resolvers = {
  Query: {
    draftRatingForBeer: (_: any, args: any, { cache }: { cache: ApolloCache<any> }) => {
      console.log("localRatingForBeer", args.beerId);
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
      console.log("setCurrentBeerId", beerId);

      const data = {
        currentBeerId: beerId
      };
      cache.writeData({ data });

      return beerId;
    },
    setDraftRatingForBeer: (
      _: any,
      { beerId, author, comment }: { beerId: string; author: string; comment: string },
      { cache }: { cache: ApolloCache<any> }
    ) => {
      console.log(`Writing draftRating for beer '${beerId}', author: '${author}', comment: '${comment}'`);

      const draftRating = {
        __typename: "DraftRating",
        id: beerId,
        author,
        comment
      };

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
        id: `DraftRating:${beerId}`
      });

      return draftRating;
    }
  }
};

export default {
  typeDefs,
  defaults,
  resolvers
};
