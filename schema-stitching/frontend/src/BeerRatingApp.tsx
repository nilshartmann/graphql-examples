import * as React from "react";
import { gql } from "apollo-boost";
import { Query } from "react-apollo";

import { BeerRatingAppQueryResult } from "./__generated__/BeerRatingAppQuery";

import * as styles from "./BeerRatingApp.scss";
import BeerList from "./BeerList";
import ServiceStatus from "./ServiceStatus";
import Header from "./Header";
import Footer from "./Footer";

const BEER_RATING_APP_QUERY = gql`
  query BeerRatingAppQuery {
    beerServiceStatus {
      name
      uptime
      javaVersion
      graphiQL
    }

    ratingServiceStatus {
      name
      nodeJsVersion
      uptime
      graphiQL
    }

    stitcherStatus {
      name
      nodeJsVersion
      uptime
      graphiQL
    }

    beers {
      id
      name
      price

      ratings {
        id
        beerId
        author
        comment
      }
    }
  }
`;

class BeerRatingQuery extends Query<BeerRatingAppQueryResult> {}

const BeerRatingApp = () => (
  <div className={styles.BeerRatingApp}>
    <BeerRatingQuery query={BEER_RATING_APP_QUERY}>
      {({ loading, error, data }) => {
        if (loading) {
          return <h1>Loading...</h1>;
        }
        if (error) {
          console.error(error);
          return <h1>Error! {error.message}</h1>;
        }

        if (!data) {
          return <h1>No data !??!!</h1>;
        }

        return (
          <React.Fragment>
            <Header>
              <ServiceStatus status={data.beerServiceStatus} />
              <ServiceStatus status={data.ratingServiceStatus} />
              <ServiceStatus status={data.stitcherStatus} />
            </Header>
            <BeerList beers={data.beers} />
          </React.Fragment>
        );
      }}
    </BeerRatingQuery>
  </div>
);

export default BeerRatingApp;
