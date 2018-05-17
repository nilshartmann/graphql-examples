import { MergeInfo } from "graphql-tools";
import { GraphQLSchema } from "graphql";

export const LinkedSchema = `
  extend type Book {

    """All Reviews for this book"""
    reviews: [Review!]!
  }
`;

export const createLinkedResolver = (reviewsSchema: GraphQLSchema) => (mergeInfo: MergeInfo) => ({
  Book: {
    reviews: {
      fragment: `fragment BookFragment on Book { id }`,
      resolve(parent: any, args: any, context: any, info: any) {
        // parent is Book
        const bookId = parent.id;

        return mergeInfo.delegateToSchema({
          schema: reviewsSchema,
          operation: "query",
          fieldName: "reviewsForBook",
          args: { bookId },
          context,
          info
        });
      }
    }
  }
});
