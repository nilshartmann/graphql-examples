import * as React from "react";
import { gql } from "apollo-boost";
import { Query, Mutation } from "react-apollo";

import * as styles from "./BeerRatingApp.scss";
import Header from "./Header";
import BeerPage from "./BeerPage";
import { BeerRatingAppQueryResult } from "./__generated__/BeerRatingAppQuery";
import { SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables } from "./__generated__/SetCurrentBeerIdMutation";
import BeerRack from "./BeerRack";
import Footer from "./Footer";
const BEER_RATING_APP_QUERY = gql`
  query BeerRatingAppQuery {
    beers {
      id
    }

    currentBeerId @client
  }
`;

const SET_CURRENT_BEER_ID_MUTATION = gql`
  mutation SetCurrentBeerIdMutation($newBeerId: ID!) {
    setCurrentBeerId(beerId: $newBeerId) @client
  }
`;

// TypeScript 2.9:
const BeerRatingApp = () => (
  <div className={styles.BeerRatingApp}>
    <Header />
    <div className={styles.Main}>
      <Query<BeerRatingAppQueryResult> query={BEER_RATING_APP_QUERY}>
        {({ loading, error, data }) => {
          if (loading) {
            return <h1>Loading...</h1>;
          }

          if (error) {
            return <h1>Error...</h1>;
          }

          const { currentBeerId } = data!;

          return (
            <Mutation<SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables> mutation={SET_CURRENT_BEER_ID_MUTATION}>
              {setCurrentBeerId => (
                <>
                  <BeerRack
                    beerIds={data!.beers}
                    onBeerSelected={beerId => setCurrentBeerId({ variables: { newBeerId: beerId } })}
                  />
                  <BeerPage beerId={currentBeerId} />
                </>
              )}
            </Mutation>
          );
        }}
      </Query>
    </div>
    <Footer />
  </div>
);

export default BeerRatingApp;
