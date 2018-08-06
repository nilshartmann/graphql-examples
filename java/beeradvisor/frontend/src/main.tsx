import * as React from "react";
import * as ReactDOM from "react-dom";

import ApolloClient from "apollo-boost";
import { ApolloProvider } from "react-apollo";

import BeerRatingApp from "./BeerRatingApp";
import { AuthProvider, getAuthToken } from "./AuthContext";
const client = new ApolloClient({
  uri: "http://localhost:9000/graphql",
  request: async operation => {
    const token = getAuthToken();
    if (token) {
      operation.setContext({
        headers: {
          authorization: `Bearer ${token}`
        }
      });
    }
  }
});

const theBeerRatingApp = (
  <ApolloProvider client={client}>
    <AuthProvider>
      <BeerRatingApp />
    </AuthProvider>
  </ApolloProvider>
);

const mountNode = document.getElementById("app");
ReactDOM.render(theBeerRatingApp, mountNode);
