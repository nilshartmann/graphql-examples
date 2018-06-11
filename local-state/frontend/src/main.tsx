import * as React from "react";
import * as ReactDOM from "react-dom";

import ApolloClient, { gql } from "apollo-boost";
import { ApolloProvider } from "react-apollo";

import BeerRatingApp from "./BeerRatingApp";
import { ApolloCache } from "apollo-cache";
import clientState from "./client-state";
// Point GraphQL Client
// https://github.com/apollographql/apollo-link-state/pull/180
// https://github.com/apollographql/apollo-link-state/blob/ff90610d311a8ebc9cc8945b0c00a12c4ce55aed/packages/apollo-link-state/src/__tests__/initialization.ts#L27

// https://github.com/apollographql/apollo-client-devtools/issues

// combine local and remote data: https://www.apollographql.com/docs/link/links/state.html#combine
// using @client with remote data: https://github.com/apollographql/apollo-link-state/issues/239
const client = new ApolloClient({
  uri: "http://localhost:9000/graphql",
  clientState
});

const theBeerRatingApp = (
  <ApolloProvider client={client}>
    <BeerRatingApp />
  </ApolloProvider>
);

const mountNode = document.getElementById("app");
ReactDOM.render(theBeerRatingApp, mountNode);
