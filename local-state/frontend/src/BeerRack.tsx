import * as React from "react";
import { gql } from "apollo-boost";
import { Mutation } from "react-apollo";
import { SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables } from "./__generated__/SetCurrentBeerIdMutation";

import * as styles from "./BeerRack.scss";
interface BeerRackProps {
  currentBeerId: string;
  beerIds: { id: string }[];
}

const SET_CURRENT_BEER_ID_MUTATION = gql`
  mutation SetCurrentBeerIdMutation($newBeerId: ID!) {
    setCurrentBeerId(beerId: $newBeerId) @client
  }
`;

export default function BeerRack({ beerIds, currentBeerId }: BeerRackProps) {
  return (
    <Mutation<SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables> mutation={SET_CURRENT_BEER_ID_MUTATION}>
      {setCurrentBeerId => (
        <div className={styles.BeerRack}>
          {beerIds.map(beerId => (
            <Thumbnail
              key={beerId.id}
              imgUrl={`/assets/beer/${beerId.id}.jpg`}
              onClick={() => setCurrentBeerId({ variables: { newBeerId: beerId.id } })}
              active={currentBeerId === beerId.id}
            />
          ))}
        </div>
      )}
    </Mutation>
  );
}

interface ThumbnailProps {
  imgUrl: string;
  onClick: () => void;
  active?: boolean;
}

function Thumbnail({ imgUrl, onClick, active }: ThumbnailProps) {
  const borderClassName = active ? `${styles.ThumbnailBorder} ${styles.Active}` : styles.ThumbnailBorder;
  return (
    <div className={styles.Thumbnail} style={{ backgroundImage: `url(${imgUrl})` }} onClick={onClick}>
      <div className={borderClassName}>
        {/* <div style={{ fontWeight: "bold", textAlign: "right", position: "relative", right: "0", color: "red" }}>*</div> */}
      </div>
    </div>
  );
}
