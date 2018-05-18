import * as React from "react";
import * as ReactDOM from "react-dom";

import ApolloClient from "apollo-boost";
import { ApolloProvider } from "react-apollo";

import BooksApp from "./BooksApp";

// Point GraphQL Client to our Schema Stitcher
const client = new ApolloClient({ uri: "http://localhost:9000/graphql" });

const theBooksApp = (
  <ApolloProvider client={client}>
    <BooksApp />
  </ApolloProvider>
);

const mountNode = document.getElementById("app");
ReactDOM.render(theBooksApp, mountNode);
