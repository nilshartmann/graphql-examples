import express from "express";
import bodyParser from "body-parser";

import { graphqlExpress, graphiqlExpress } from "apollo-server-express";

import { introspectSchema, makeRemoteExecutableSchema, mergeSchemas } from "graphql-tools";
import { HttpLink } from "apollo-link-http";
import fetch from "node-fetch";

async function createRemoteSchema(uri: string) {
  const link = new HttpLink({ uri, fetch });
  const schema = await introspectSchema(link);

  return makeRemoteExecutableSchema({
    schema,
    link
  });
}

(async () => {
  const booksSchema = await createRemoteSchema("http://localhost:9010/graphql");
  const reviewsSchema = await createRemoteSchema("http://localhost:9020/graphql");

  const combinedSchema = mergeSchemas({
    schemas: [booksSchema, reviewsSchema]
  });

  const PORT = 9000;
  const app = express();

  app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: combinedSchema }));
  app.get("/graphiql", graphiqlExpress({ endpointURL: "/graphql" })); // if you want GraphiQL enabled

  app.listen(PORT, () => {
    console.log(`Schema Stitcher  running on ${PORT}`);
    console.log(`Schema Stitcher  GraphiQL http://localhost:${PORT}/graphiql`);
  });
})();
