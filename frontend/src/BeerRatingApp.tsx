import * as React from "react";
import { gql } from "apollo-boost";

import { Query } from "react-apollo";

import { BeerRatingAppQueryResult } from "./__generated__/BeerRatingAppQuery";

import BeerList from "./BeerList";
import ServiceStatus from "./ServiceStatus";
import Footer from "./Footer";

const BOOKS_APP_QUERY = gql`
  query BeerRatingAppQuery {
    beerServiceStatus {
      name
      uptime
      javaVersion
    }

    ratingServiceStatus {
      name
      nodeJsVersion
      uptime
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

class BooksAppQuery extends Query<BeerRatingAppQueryResult> {}

const BooksApp = () => (
  <div>
    <BooksAppQuery query={BOOKS_APP_QUERY}>
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
            <BeerList beers={data.beers} />
            <Footer>
              <ServiceStatus status={data.beerServiceStatus} />
              <ServiceStatus status={data.ratingServiceStatus} />
            </Footer>
          </React.Fragment>
        );
      }}
    </BooksAppQuery>
  </div>
);

export default BooksApp;
