const express = require("express");
const bodyParser = require("body-parser");
const { graphqlExpress, graphiqlExpress } = require("apollo-server-express");
const { makeExecutableSchema } = require("graphql-tools");

const typeDefs = `
 type Query { hello: String! }
`;

const resolvers = {
  Query: { hello: () => "Hello, World" }
};

const schema = makeExecutableSchema({
  typeDefs,
  resolvers
});
const app = express();

app.use("/graphql", bodyParser.json(), graphqlExpress({ schema }));
app.use("/graphiql", express.static(`${__dirname}/graphiql`));

app.listen(9090, () => {
  console.log(`👍 Rating Backend running on9090`);
  console.log(`👍 Rating         GraphiQL http://localhost:9090/graphiql`);
});
