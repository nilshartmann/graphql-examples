import { MergeInfo } from "graphql-tools";
import { GraphQLSchema } from "graphql";

export const createLinkedSchema = (reviewsSchema: GraphQLSchema) => ({
  schema: `
  extend type Book {

    """All Reviews for this book"""
    reviews: [Review!]!
  }
`,
  resolvers: {
    Book: {
      reviews: {
        fragment: `fragment BookFragment on Book { id }`,
        resolve(parent: any, args: any, context: any, info: any) {
          // parent is Book
          const bookId = parent.id;

          return info.mergeInfo.delegateToSchema({
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
  }
});
