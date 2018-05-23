import express from "express";
import bodyParser from "body-parser";
import { graphqlExpress, graphiqlExpress } from "apollo-server-express";

import createRatingSchema from "./RatingSchema";

const PORT = 9020;
const app = express();

app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: createRatingSchema(PORT) }));
app.get("/graphiql", graphiqlExpress({ endpointURL: "/graphql" })); // if you want GraphiQL enabled

app.listen(PORT, () => {
  console.log(`Rating Backend running on ${PORT}`);
  console.log(`Rating         GraphiQL http://localhost:${PORT}/graphiql`);
});
