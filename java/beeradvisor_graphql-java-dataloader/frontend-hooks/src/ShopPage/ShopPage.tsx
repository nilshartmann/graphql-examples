import * as React from "react";
import { RouteComponentProps } from "react-router";

import styles from "./ShopPage.module.scss";

import gql from "graphql-tag";
import { QueryResult } from "@apollo/react-common";
import { useQuery } from "@apollo/react-hooks";

import { ShopPageQuery, ShopPageQueryVariables } from "./querytypes/ShopPageQuery";

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

  const theShop = data.shop;

  return (
    <div className={styles.ShopPage}>
      <div className={styles.DescriptionTitle}>
        <h1>{theShop.name}</h1>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ marginRight: "50px" }}>
          <div className={styles.Title}>
            <h1>where to find</h1>
          </div>
          <div>
            <div className={styles.Address}>
              {theShop.address.street}
              <br />
              {theShop.address.postalCode} {theShop.address.city}
              <br />
              {theShop.address.country}
            </div>
          </div>
        </div>
        <div className={styles.Title}>
          <h1>what's in stock</h1>

          <div className={styles.Beers}>
            {theShop.beers.map(b => (
              <div key={b.id} className={styles.Beer} onClick={() => history.push(`/beer/${b.id}`)}>
                {b.name}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
