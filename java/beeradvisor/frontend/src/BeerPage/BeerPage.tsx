import * as React from "react";
import { Query } from "react-apollo";
import gql from "graphql-tag";
import { BeerPageQueryResult } from "./__generated__/BeerPageQuery";
import Beer from "../BeerPage/Beer";

const BEER_PAGE_QUERY = gql`
  query BeerPageQuery($beerId: ID!) {
    beer(beerId: $beerId) {
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

    draftRating: draftRatingForBeer(beerId: $beerId) @client {
      id
      author
      comment
    }
  }
`;

class BeerPageQuery extends Query<BeerPageQueryResult> {}

interface BeerPageProps {
  beerId: string;
}

const BeerPage = ({ beerId }: BeerPageProps) => (
  <div>
    <Query<BeerPageQueryResult> query={BEER_PAGE_QUERY} variables={{ beerId }}>
      {({ loading, error, data }) => {
        if (loading) {
          return <h1>Loading...</h1>;
        }
        if (error) {
          console.error(error);
          return <h1>Error! {error.message}</h1>;
        }

        const { beer, draftRating } = data!;

        if (beer === null) {
          //
          return <h1>Beer Not found</h1>;
        }

        return <Beer beer={beer} />;
      }}
    </Query>
  </div>
);

export default BeerPage;
