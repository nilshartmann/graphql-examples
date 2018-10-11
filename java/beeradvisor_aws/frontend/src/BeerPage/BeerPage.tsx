import * as React from "react";
import { Query } from "react-apollo";
import gql from "graphql-tag";
import { BeerPageQuery as BeerPageQueryResult } from "./__generated__/BeerPageQuery";
import Beer from "../BeerPage/Beer";
import { RatingSubscription as RatingSubscriptionResult } from "./__generated__/RatingSubscription";
import { RouteComponentProps } from "react-router";

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


interface BeerPageProps extends RouteComponentProps<{ beerId: string }> { }

const BeerPage = ({
  history,
  match: {
    params: { beerId }
  }
}: BeerPageProps) => (
    <div>
      <Query<BeerPageQueryResult> query={BEER_PAGE_QUERY} variables={{ beerId: beerId }} fetchPolicy="cache-and-network">
        {({ loading, error, data, subscribeToMore }) => {
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
            <Beer
              beer={beer}
              onShopClicked={newShopId => history.push(`/shop/${newShopId}`)}
              subscribeToNewData={() =>
                subscribeToMore({
                  document: RATING_SUBSCRIPTION,
                  variables: {
                    beerId
                  },
                  updateQuery: (prev, { subscriptionData }) => {
                    if (prev.beer === null) {
                      return prev;
                    }
                    // wrong type in Apollo typedefs ...
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
          );
        }}
      </Query>
    </div>
  );

export default BeerPage;
