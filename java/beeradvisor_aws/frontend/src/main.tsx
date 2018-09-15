import * as React from "react";
import * as ReactDOM from "react-dom";

import { ApolloProvider } from "react-apollo";

import BeerRatingApp from "./BeerRatingApp";
import { AuthProvider } from "./AuthContext";
import createApolloClient from "./createApolloClient";
const client = createApolloClient();

const theBeerRatingApp = (
  <ApolloProvider client={client}>
    <AuthProvider>
      <BeerRatingApp />
    </AuthProvider>
  </ApolloProvider>
);

const mountNode = document.getElementById("app");
ReactDOM.render(theBeerRatingApp, mountNode);
