import * as React from "react";
import * as styles from "./BeerList.scss";

import Beer from "./Beer";

import { BeerRatingAppQueryResult_beers as BeerData } from "./__generated__/BeerRatingAppQuery";

interface BeerListProps {
  beers: BeerData[];
}

export default function BeerList({ beers }: BeerListProps) {
  return <div className={styles.BeerList}>{beers.map(beer => <Beer key={beer.id} beer={beer} />)}</div>;
}
