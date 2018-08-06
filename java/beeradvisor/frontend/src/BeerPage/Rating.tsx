import * as React from "react";
import * as styles from "./Beer.scss";

import { BeerPageQueryResult_beer_ratings as BeerRatingData } from "./__generated__/BeerPageQuery";

interface RatingProps {
  rating: BeerRatingData;
}

const Rating = ({ rating: { id, author, comment, stars } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author.name}</span>:{" "}
    <span className={styles.Comment}>
      „{comment}“ ({stars}/5)
    </span>
  </div>
);

export default Rating;
