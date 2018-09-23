import * as React from "react";
import * as styles from "./Beer.scss";

import { BeerPageQuery_beer_ratings as BeerRatingData } from "./__generated__/BeerPageQuery";

interface RatingProps {
  rating: BeerRatingData;
}

const Stars = ({ stars }: { stars: number }) => {
  const x = new Array(5).fill(undefined).map((_, ix) => (
    <span key={ix} className={ix < stars ? styles.filled : null}>
      ☆
    </span>
  ));
  return <div className={styles.Stars}>{x}</div>;
};

const Rating = ({ rating: { author, comment, stars } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author.name}</span>:{" "}
    <span className={styles.Comment}>
      „{comment}“ <Stars stars={stars} />
    </span>
  </div>
);

export default Rating;
