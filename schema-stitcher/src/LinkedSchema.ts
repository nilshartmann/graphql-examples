import { MergeInfo } from "graphql-tools";
import { GraphQLSchema } from "graphql";

export const createLinkedSchema = (ratingSchema: GraphQLSchema) => ({
  linkedSchema: `
  extend type Beer {

    """All Ratings for this Beer"""
    ratings: [Rating!]!
  }
`,
  linkedSchemaResolvers: {
    Beer: {
      ratings: {
        fragment: `fragment BeerFragment on Beer { id }`,
        resolve(parent: any, args: any, context: any, info: any) {
          // parent is Beer
          const beerId = parent.id;

          return info.mergeInfo.delegateToSchema({
            schema: ratingSchema,
            operation: "query",
            fieldName: "ratingsForBeer",
            args: { beerId },
            context,
            info
          });
        }
      }
    }
  }
});
