import * as React from "react";
import { RouteComponentProps } from "react-router";

import gql from "graphql-tag";
import { QueryResult } from "@apollo/react-common";
import { useQuery } from "@apollo/react-hooks";

import { ShopPageQuery, ShopPageQueryVariables } from "./querytypes/ShopPageQuery";
import Shop from "./Shop";

const SHOP_PAGE_QUERY = gql`
  query ShopPageQuery($shopId: ID!) {
    shop(shopId: $shopId) {
      id
      name

      address {
        street
        postalCode
        city
        country
      }
      beers {
        id
        name
      }
    }
  }
`;

type ShopPageProps = RouteComponentProps<{ shopId: string }>;
type ShopPageQueryResult = QueryResult<ShopPageQuery, ShopPageQueryVariables>;

export default function ShopPage({ match, history }: ShopPageProps) {
  const { data, loading, error }: ShopPageQueryResult = useQuery(SHOP_PAGE_QUERY, {
    variables: { shopId: match.params.shopId }
  });

  if (loading) {
    return <h1>Please wait... Shop is loading...</h1>;
  }
  if (error) {
    console.error("Loading Shop failed: ", error);
    return <h1>Sorry... Loading shop failed</h1>;
  }

  if (!data || !data.shop) {
    return <h1>No data ?</h1>;
  }

  return <Shop shop={data.shop} onBeerClick={beerId => history.push(`/beer/${beerId}`)} />;
}
