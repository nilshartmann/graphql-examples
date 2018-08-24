import { ApolloClient } from "apollo-client";
import { InMemoryCache } from "apollo-cache-inmemory";
import { HttpLink } from "apollo-link-http";
import { setContext } from "apollo-link-context";
import { onError } from "apollo-link-error";
import { ApolloLink } from "apollo-link";
import { setAuthToken, getAuthToken } from "./AuthContext";
export default function createApolloClient() {
  const httpLink = new HttpLink({
    uri: "http://localhost:9000/graphql"
  });

  const errorLink = onError(({ graphQLErrors, networkError }) => {
    if (graphQLErrors)
      graphQLErrors.map(({ message, locations, path }) =>
        console.log(`[GraphQL error]: Message: ${message}, Location: ${locations}, Path: ${path}`)
      );
    if (networkError) {
      // no real way to interpret return value, so remove token in all cases
      setAuthToken(null);
      console.log(`[Network error]: ${networkError}`);
    }
  });

  const authLink = setContext((_, { headers }) => {
    const token = getAuthToken();
    return {
      headers: {
        ...headers,
        authorization: token ? `Bearer ${token}` : ""
      }
    };
  });

  const client = new ApolloClient({
    link: ApolloLink.from([errorLink, authLink, httpLink]),
    cache: new InMemoryCache()
  });

  return client;
}
