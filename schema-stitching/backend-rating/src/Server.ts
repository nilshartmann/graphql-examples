import express from "express";
import bodyParser from "body-parser";
import { graphqlExpress } from "apollo-server-express";

import createRatingSchema from "./RatingSchema";

const PORT = 9020;
const app = express();

app.use("/graphql", bodyParser.json(), graphqlExpress({ schema: createRatingSchema(PORT) }));
app.use("/graphiql", express.static(`${__dirname}/graphiql`));

app.listen(PORT, () => {
  console.log(`👍 Rating Backend running on ${PORT}`);
  console.log(`👍 Rating         GraphiQL http://localhost:${PORT}/graphiql`);
});
