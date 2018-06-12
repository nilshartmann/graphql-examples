import * as React from "react";
import { gql } from "apollo-boost";
import { Mutation } from "react-apollo";
import { SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables } from "./__generated__/SetCurrentBeerIdMutation";

import * as styles from "./BeerRack.scss";
interface BeerRackProps {
  beerIds: { id: string }[];
}

const SET_CURRENT_BEER_ID_MUTATION = gql`
  mutation SetCurrentBeerIdMutation($newBeerId: ID!) {
    setCurrentBeerId(beerId: $newBeerId) @client
  }
`;

export default function BeerRack({ beerIds }: BeerRackProps) {
  // // Alternate:
  // client.mutate<SetCurrentBeerIdMutationResult>({
  //   mutation: SET_CURRENT_BEER_ID_MUTATION,
  //   variables: { newBeerId: currentBeerId }
  // });

  return (
    <Mutation<SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables> mutation={SET_CURRENT_BEER_ID_MUTATION}>
      {setCurrentBeerId => (
        <div className={styles.BeerRack}>
          {beerIds.map(beerId => (
            <div
              key={beerId.id}
              className={styles.Thumbnail}
              style={{ backgroundImage: `url('/assets/beer/${beerId.id}.jpg')` }}
              onClick={() => setCurrentBeerId({ variables: { newBeerId: beerId.id } })}
            />
          ))}
        </div>
      )}
    </Mutation>
  );
}
