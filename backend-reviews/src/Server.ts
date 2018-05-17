import express from "express";
import bodyParser from "body-parser";
import { graphqlExpress, graphiqlExpress } from "apollo-server-express";

import ReviewSchema from "./ReviewSchema";

const PORT = 9020;
const app = express();

app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: ReviewSchema }));
app.get("/graphiql", graphiqlExpress({ endpointURL: "/graphql" })); // if you want GraphiQL enabled

app.listen(PORT, () => {
  console.log(`Reviews Backend running on ${PORT}`);
});
