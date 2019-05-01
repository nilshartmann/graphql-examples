import { ApolloClient } from "apollo-client";
import { InMemoryCache } from "apollo-cache-inmemory";
import { split } from "apollo-link";
import { HttpLink } from "apollo-link-http";
import { setContext } from "apollo-link-context";
import { onError } from "apollo-link-error";
import { ApolloLink } from "apollo-link";
import { setAuthToken, getAuthToken } from "./AuthContext";
import { WebSocketLink } from "apollo-link-ws";
import { getMainDefinition } from "apollo-utilities";

const isLocalDev = window.location.hostname === "localhost";

export default function createApolloClient() {
  const httpLink = new HttpLink({
    uri: isLocalDev ? "http://localhost:9000/graphql" : "/graphql",
    credentials: "include"
  });

  const wsLink = new WebSocketLink({
    uri: isLocalDev ? "ws://localhost:9000/subscriptions" : `ws://${window.location.host}/subscriptions`,
    options: {
      reconnect: true
    }
  });

  // using the ability to split links, you can send data to each link
  // depending on what kind of operation is being sent
  const remoteLink = split(
    // split based on operation type
    ({ query }) => {
      const def = getMainDefinition(query);
      return def.kind === "OperationDefinition" && def.operation === "subscription";
    },
    wsLink,
    httpLink
  );

  const errorLink = onError(({ graphQLErrors, networkError }) => {
    if (graphQLErrors) {
      graphQLErrors.map(({ message, locations, path }) =>
        console.log(`[GraphQL error]: Message: ${message}, Location: ${locations}, Path: ${path}`)
      );
    }
    if (networkError) {
      // no real way to interpret return value, so remove token in all cases
      setAuthToken(null);
      console.log(`[Network error]: ${networkError}`);
    }
  });

  const authLink = setContext((_, { headers }) => {
    const token = getAuthToken();
    if (token) {
      return {
        headers: {
          ...headers,
          authorization: `Bearer ${token}`
        }
      };
    }
    return headers;
  });

  const client = new ApolloClient({
    link: ApolloLink.from([errorLink, authLink, remoteLink]),
    cache: new InMemoryCache()
  });

  return client;
}
