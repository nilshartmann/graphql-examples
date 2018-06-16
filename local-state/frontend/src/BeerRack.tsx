import * as React from "react";
import { gql } from "apollo-boost";
import { Mutation } from "react-apollo";
import { SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables } from "./__generated__/SetCurrentBeerIdMutation";

import * as styles from "./BeerRack.scss";
interface BeerRackProps {
  currentBeerId: string;
  beers: { id: string; hasDraftRating: boolean }[];
}

const SET_CURRENT_BEER_ID_MUTATION = gql`
  mutation SetCurrentBeerIdMutation($newBeerId: ID!) {
    setCurrentBeerId(beerId: $newBeerId) @client
  }
`;

export default function BeerRack({ beers, currentBeerId }: BeerRackProps) {
  return (
    <Mutation<SetCurrentBeerIdMutationResult, SetCurrentBeerIdMutationVariables> mutation={SET_CURRENT_BEER_ID_MUTATION}>
      {setCurrentBeerId => (
        <div className={styles.BeerRack}>
          {beers.map(beer => (
            <Thumbnail
              key={beer.id}
              imgUrl={`/assets/beer/${beer.id}.jpg`}
              onClick={() => setCurrentBeerId({ variables: { newBeerId: beer.id } })}
              dirty={beer.hasDraftRating}
              active={currentBeerId === beer.id}
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
  dirty: boolean;
  active?: boolean;
}

function Thumbnail({ imgUrl, onClick, dirty, active }: ThumbnailProps) {
  const borderClassName = active ? `${styles.ThumbnailBorder} ${styles.Active}` : styles.ThumbnailBorder;
  return (
    <div className={styles.Thumbnail} style={{ backgroundImage: `url(${imgUrl})` }} onClick={onClick}>
      <div className={borderClassName}>
        {dirty && (
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
            <path d="M478.21 334.093L336 256l142.21-78.093c11.795-6.477 15.961-21.384 9.232-33.037l-19.48-33.741c-6.728-11.653-21.72-15.499-33.227-8.523L296 186.718l3.475-162.204C299.763 11.061 288.937 0 275.48 0h-38.96c-13.456 0-24.283 11.061-23.994 24.514L216 186.718 77.265 102.607c-11.506-6.976-26.499-3.13-33.227 8.523l-19.48 33.741c-6.728 11.653-2.562 26.56 9.233 33.037L176 256 33.79 334.093c-11.795 6.477-15.961 21.384-9.232 33.037l19.48 33.741c6.728 11.653 21.721 15.499 33.227 8.523L216 325.282l-3.475 162.204C212.237 500.939 223.064 512 236.52 512h38.961c13.456 0 24.283-11.061 23.995-24.514L296 325.282l138.735 84.111c11.506 6.976 26.499 3.13 33.227-8.523l19.48-33.741c6.728-11.653 2.563-26.559-9.232-33.036z" />
          </svg>
        )}
      </div>
    </div>
  );
}
