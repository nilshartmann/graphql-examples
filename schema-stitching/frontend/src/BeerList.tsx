import * as React from "react";
import * as styles from "./BeerList.scss";
import {
  BeerRatingAppQueryResult_beers as BeerData,
  BeerRatingAppQueryResult_beers_ratings as BeerRatingData
} from "./__generated__/BeerRatingAppQuery";

interface RatingProps {
  rating: BeerRatingData;
}

const Rating = ({ rating: { id, author, comment } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author}</span>: <span className={styles.Comment}>„{comment}“</span>
  </div>
);

interface BeerProps {
  beer: BeerData;
}
const Beer = ({ beer: { id, name, price, ratings } }: BeerProps) => (
  <div className={styles.Beer}>
    <div className={styles.Img}>
      <img src={`/assets/beer/${id}.jpg`} />
    </div>
    <div className={styles.Description}>
      <div className={styles.DescriptionTitle}>
        <h1>{name}</h1>
        <h3>{price}</h3>
      </div>
      <div className={styles.Ratings}>
        <h1>What customers say:</h1>
        {ratings.map(rating => <Rating key={rating.id} rating={rating} />)}
      </div>
    </div>
  </div>
);

interface BeerListProps {
  beers: BeerData[];
}
const BeerList = ({ beers }: BeerListProps) => (
  <div className={styles.BeerList}>{beers.map(beer => <Beer key={beer.id} beer={beer} />)}</div>
);

export default BeerList;
