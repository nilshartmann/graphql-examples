import express from "express";
import bodyParser from "body-parser";
import { graphqlExpress, graphiqlExpress } from "apollo-server-express";

import createReviewSchema from "./ReviewSchema";

const PORT = 9020;
const app = express();

app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: createReviewSchema(PORT) }));
app.get("/graphiql", graphiqlExpress({ endpointURL: "/graphql" })); // if you want GraphiQL enabled

app.listen(PORT, () => {
  console.log(`Reviews Backend running on ${PORT}`);
  console.log(`Reviews Stitcher  GraphiQL http://localhost:${PORT}/graphiql`);
});
