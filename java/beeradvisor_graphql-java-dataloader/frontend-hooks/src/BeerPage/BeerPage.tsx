import React from "react";
import { RouteComponentProps } from "react-router-dom";

import gql from "graphql-tag";
import { useQuery } from "@apollo/react-hooks";
import { QueryResult } from "@apollo/react-common";
import Beer from "BeerPage/Beer";
import { BeerPageQuery, BeerPageQueryVariables } from "./querytypes/BeerPageQuery";
import { RatingSubscription as RatingSubscriptionResult } from "./querytypes/RatingSubscription";
import UpdateBeer from "./UpdateBeer";

const BEER_PAGE_QUERY = gql`
  query BeerPageQuery($beerId: ID!) {
    beer(beerId: $beerId) {
      id
      name
      price
      ratings {
        id
        stars

        beer {
          id
        }
        author {
          name
        }
        comment
      }
      shops {
        id
        name
      }
    }
  }
`;

const RATING_SUBSCRIPTION = gql`
  subscription RatingSubscription($beerId: ID!) {
    rating: newRatings(beerId: $beerId) {
      id
      stars
      beer {
        id
      }
      author {
        name
      }
      comment
    }
  }
`;

type BeerPageProps = RouteComponentProps<{ beerId: string }>;
type BeerPageQueryResult = QueryResult<BeerPageQuery, BeerPageQueryVariables>;

export default function BeerPage({ history, match }: BeerPageProps) {
  const variables = { beerId: match.params.beerId };
  const { loading, error, data, subscribeToMore }: BeerPageQueryResult = useQuery(BEER_PAGE_QUERY, {
    variables,
    fetchPolicy: "cache-and-network"
  });

  if (loading) {
    return <h1>Loading...</h1>;
  }
  if (error) {
    console.error(error);
    return <h1>Error! {error.message}</h1>;
  }

  const { beer } = data!;

  if (beer === null) {
    //
    return <h1>Beer Not found</h1>;
  }

  return (
    <div>
      <Beer
        beer={beer}
        onShopClicked={newShopId => history.push(`/shop/${newShopId}`)}
        subscribeToNewData={() =>
          subscribeToMore({
            document: RATING_SUBSCRIPTION,
            variables,
            updateQuery: (prev, { subscriptionData }) => {
              if (prev.beer === null) {
                return prev;
              }
              // wrong type in Apollo typedefs imho (you cannot specify own type for 'subscribeToMore' result)...
              const newRating: RatingSubscriptionResult = subscriptionData.data as any;
              const newRatings = [...prev.beer.ratings, newRating.rating];
              return {
                beer: {
                  ...prev.beer,
                  ratings: newRatings
                }
              };
            }
          })
        }
      />
      <UpdateBeer beer={beer} />
    </div>
  );
}
