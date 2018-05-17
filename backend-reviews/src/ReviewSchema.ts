const { makeExecutableSchema } = require("graphql-tools");

import { Review, REVIEWS } from "./Model";

const typeDefs = `
  type Review {
    """An immutable unique Id"""
    id: ID!

    """The id of the book, this review is written for"""
    bookId: ID!

    """The review itself"""
    review: String!
  }

  type ProcessInfo {
    name: String!
    port: Int!
    uptime: String!
  }


  type Query {
    """All reviews stored in our system"""
    reviews: [Review!]! 
    
    """Return all reviews for the Book with the specified Id"""
    reviewsForBook(bookId: ID!): [Review!]! 

    """Returns health information about the running **Review** process"""
    ping: ProcessInfo
  }
`;

const createReviewSchema = (port: number) => {
  const bootTime = Date.now();

  // The resolvers
  const resolvers = {
    Query: {
      reviews: (): Review[] => REVIEWS,
      reviewsForBook: (_: any, { bookId }: { bookId: string }): Review[] => REVIEWS.filter(r => r.bookId === bookId),
      ping: () => ({
        name: "Reviews Backend",
        port,
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

export default createReviewSchema;
