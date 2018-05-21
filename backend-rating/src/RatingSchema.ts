const { makeExecutableSchema } = require("graphql-tools");

import { Rating, RATINGS } from "./Model";

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
    uptime: String!
  }


  type Query {
    """All ratings stored in our system"""
    ratings: [Rating!]! 
    
    """Return all ratings for the Beer with the specified Id"""
    ratingsForBeer(beerId: ID!): [Rating!]! 

    """Returns health information about the running **Rating** process"""
    ping: ProcessInfo!
  }
`;

const createRatingSchema = (port: number) => {
  const bootTime = Date.now();

  // The resolvers
  const resolvers = {
    Query: {
      ratings: (): Rating[] => RATINGS,
      ratingsForBeer: (_: any, { beerId }: { beerId: string }): Rating[] => RATINGS.filter(r => r.beerId === beerId),
      ping: () => ({
        name: "Rating Backend",
        nodeJsVersion: process.versions.node,
        uptime: `${(Date.now() - bootTime) / 1000}s`
      })
    }
  };

  // Put together a schema
  return makeExecutableSchema({
    typeDefs,
    resolvers
  });
};

export default createRatingSchema;
