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
  type Query {
    """All reviews stored in our system"""
    reviews: [Review!]! 
    
    """All Channels that contain the specified Member"""
    reviewsForBook(bookId: ID!): [Review!]! 
  }
`;

// The resolvers
const resolvers = {
  Query: {
    reviews: (): Review[] => REVIEWS,
    reviewsForBook: (_: any, { bookId }: { bookId: string }): Review[] => REVIEWS.filter(r => r.bookId === bookId)
  }
};

// Put together a schema
const ReviewSchema = makeExecutableSchema({
  typeDefs,
  resolvers
});

export default ReviewSchema;
