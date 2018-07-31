import * as React from "react";
import { gql } from "apollo-boost";
import { Mutation, Query } from "react-apollo";

import { BeerRackQueryResult } from "./__generated__/BeerRackQuery";

import * as styles from "./BeerRack.scss";
interface BeerRackProps {
  currentBeerId: string;
  setCurrentBeerId(newCurrentBeerId: string): void;
}

const BEER_RACK_QUERY = gql`
  query BeerRackQuery {
    beers {
      id
    }
  }
`;

export default function BeerRack({ currentBeerId, setCurrentBeerId }: BeerRackProps) {
  return (
    <div className={styles.BeerRack}>
      <Query<BeerRackQueryResult> query={BEER_RACK_QUERY}>
        {({ data, error, loading }) => {
          if (error) {
            return <h1>Error while loading Beers 😱</h1>;
          }
          if (loading) {
            return <h1>Please stay tuned - Beers are loading . . .</h1>;
          }
          const { beers } = data!;
          return beers.map(beer => (
            <Thumbnail
              key={beer.id}
              imgUrl={`/assets/beer/${beer.id}.jpg`}
              onClick={() => setCurrentBeerId(beer.id)}
              active={currentBeerId === beer.id}
            />
          ));
        }}
      </Query>
    </div>
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
      <div className={borderClassName} />
    </div>
  );
}
