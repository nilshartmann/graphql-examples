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
        // with the fragment we make sure, that the id field on Beer
        // is always queried (even if not specified on original query),
        // so that we can access it in our resolver
        fragment: `fragment BeerFragment on Beer { id }`,
        resolve(parent: any, args: any, context: any, info: any) {
          // parent is Beer
          console.log(parent);
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
