const { makeExecutableSchema } = require("graphql-tools");

import { RatingDB, RatingStore } from "./RatingStore";

const typeDefs = `
  type Rating {
    """An immutable unique Id"""
    id: ID!

    """The id of the beer, this rating is written for"""
    beerId: ID!

    """Who has written this rating?"""
    author: String!

    """The rating itself"""
    comment: String!
  }

  type ProcessInfo {
    name: String!
    nodeJsVersion: String!
    uptime: String!,
    graphiQL: String
  }


  type Query {
    """All ratings stored in our system"""
    ratings: [Rating!]! 
    
    """Return all ratings for the Beer with the specified Id"""
    ratingsForBeer(beerId: ID!): [Rating!]! 

    """Returns health information about the running **Rating** process"""
    ping: ProcessInfo!
  }

  input AddRatingInput {
    beerId: String!
    author: String!
    comment: String!
  }

  type Mutation {
    """Add a new Rating to a Beer and returns the new Rating"""
    addRating(ratingInput: AddRatingInput): Rating!
  }
`;

type Rating = RatingDB;

interface AddRatingInput {
  beerId: string;
  author: string;
  comment: string;
}

const createRatingSchema = (port: number) => {
  const bootTime = Date.now();

  const ratingStore = new RatingStore();
  // The resolvers
  const resolvers = {
    Query: {
      ratings: (): Rating[] => ratingStore.all(),
      ratingsForBeer: (_: any, { beerId }: { beerId: string }): Rating[] => ratingStore.all().filter(r => r.beerId === beerId),
      ping: () => ({
        name: "👍 Rating Backend",
        nodeJsVersion: process.versions.node,
        uptime: `${(Date.now() - bootTime) / 1000}s`,
        graphiQL: `http://localhost:${port}/graphiql`
      })
    },
    Mutation: {
      addRating: (_: any, { ratingInput }: { ratingInput: AddRatingInput }): Rating => {
        const { beerId, author, comment } = ratingInput;
        return ratingStore.newRating(beerId, author, comment);
      }
    }
  };

  // Put together a schema
  return makeExecutableSchema({
    typeDefs,
    resolvers
  });
};

export default createRatingSchema;
