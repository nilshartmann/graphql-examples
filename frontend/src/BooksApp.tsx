import * as React from "react";
import { gql } from "apollo-boost";

import { Query } from "react-apollo";

import { BooksAppQueryResult } from "./__generated__/BooksAppQuery";

import BookList from "./BookList";
import ServiceStatus from "./ServiceStatus";
import Footer from "./Footer";

const BOOKS_APP_QUERY = gql`
  query BooksAppQuery {
    booksServiceStatus {
      name
      javaVersion
      uptime
    }
    reviewsServiceStatus {
      name
      nodeJsVersion
      uptime
    }
    books {
      id
      title
      author
      reviews {
        id
        author
        review
      }
    }
  }
`;

class BooksAppQuery extends Query<BooksAppQueryResult> {}

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
            <BookList books={data.books} />
            <Footer>
              <ServiceStatus status={data.booksServiceStatus} />
              <ServiceStatus status={data.reviewsServiceStatus} />
            </Footer>
          </React.Fragment>
        );
      }}
    </BooksAppQuery>
  </div>
);

export default BooksApp;
