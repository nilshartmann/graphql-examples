import express from "express";
import bodyParser from "body-parser";
import cors from "cors";

import { graphqlExpress, graphiqlExpress } from "apollo-server-express";

import {
  introspectSchema,
  makeRemoteExecutableSchema,
  mergeSchemas,
  transformSchema,
  RenameTypes,
  RenameRootFields,
  makeExecutableSchema
} from "graphql-tools";
import { HttpLink } from "apollo-link-http";
import fetch from "node-fetch";
import { GraphQLSchema } from "graphql";
import { createLinkedSchema } from "./LinkedSchema";
import { create } from "domain";

const PORT = 9000;

async function createRemoteSchema(uri: string) {
  const link = new HttpLink({ uri, fetch });
  const schema = await introspectSchema(link);

  return makeRemoteExecutableSchema({
    schema,
    link
  });
}

function renameSystemInfo(schema: GraphQLSchema, systemName: String) {
  return transformSchema(schema, [
    new RenameTypes((name: string) => (name === "ProcessInfo" ? `${systemName}Status` : undefined)),
    new RenameRootFields(
      (_operation: string, name: string) =>
        name === "ping" ? `${systemName.substring(0, 1).toLowerCase()}${systemName.substring(1)}Status` : name
    )
  ]);
}

/** Own, "local" schema of the Stitcher */
function createStitcherSchema() {
  const bootTime = Date.now();
  const stitcherSchema = makeExecutableSchema({
    typeDefs: `
      type StitcherStatus {
        name: String!
        nodeJsVersion: String!
        uptime: String!
        graphiQL: String
      }
  
      type Query {
        stitcherStatus: StitcherStatus!
      }
    `,
    resolvers: {
      Query: {
        stitcherStatus: () => ({
          name: "🖇 Stitcher",
          nodeJsVersion: process.versions.node,
          uptime: `${(Date.now() - bootTime) / 1000}s`,
          graphiQL: `http://localhost:${PORT}/graphiql`
        })
      }
    }
  });
  return stitcherSchema;
}

async function createCombinedSchema() {
  // STEP 1: Create the Remote Schemas
  const beerSchema = await createRemoteSchema("http://localhost:9010/graphql");
  const ratingSchema = await createRemoteSchema("http://localhost:9020/graphql");

  // STEP 2: Transform the Remote Schemas (optional)
  const transformedBeerSchema = renameSystemInfo(beerSchema, "BeerService");
  const transformedRatingSchema = renameSystemInfo(ratingSchema, "RatingService");

  // STEP 3: Create the 'linkedSchema' that connects Beers with Ratings
  const { linkedSchema, linkedSchemaResolvers } = createLinkedSchema(ratingSchema);

  // STEP 4: Merge Schemas
  return mergeSchemas({
    schemas: [
      transformedBeerSchema, //
      transformedRatingSchema,
      linkedSchema,
      createStitcherSchema()
    ],
    resolvers: linkedSchemaResolvers
  });
}

(async () => {
  const combinedSchema = await createCombinedSchema();

  const app = express();

  app.use(cors());
  app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: combinedSchema }));
  app.get("/graphiql", graphiqlExpress({ endpointURL: "/graphql" }));

  app.listen(PORT, () => {
    console.log(`Schema Stitcher  running on ${PORT}`);
    console.log(`Schema Stitcher  GraphiQL http://localhost:${PORT}/graphiql`);
  });
})();
