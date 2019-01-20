import * as React from "react";
import * as styles from "./Beer.scss";

import { BeerPageQuery_beer_ratings as BeerRatingData } from "./__generated__/BeerPageQuery";
import Stars from "../components";

interface RatingProps {
  rating: BeerRatingData;
}

const Rating = ({ rating: { author, comment, stars } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author.name}</span>:{" "}
    <span className={styles.Comment}>
      „{comment}“ <Stars stars={stars} />
    </span>
  </div>
);

export default Rating;
