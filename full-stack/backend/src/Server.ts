import express from "express";
import bodyParser from "body-parser";
import { graphqlExpress, graphiqlExpress } from "apollo-server-express";

import createBeerRatingSchema from "./Schema";

const PORT = 9000;
const app = express();

const beerRatingSchema = createBeerRatingSchema(PORT);

app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: beerRatingSchema }));
app.get("/graphiql", graphiqlExpress({ endpointURL: "/graphql" }));

app.listen(PORT, () => {
  console.log(`🍺  👍  BeerRating Backend running on ${PORT}`);
  console.log(`🍺  👍  BeerRating GraphiQL http://localhost:${PORT}/graphiql`);
});
