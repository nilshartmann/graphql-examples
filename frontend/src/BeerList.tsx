import * as React from "react";

import {
  BeerRatingAppQueryResult_beers as BeerData,
  BeerRatingAppQueryResult_beers_ratings as BeerRatingData
} from "./__generated__/BeerRatingAppQuery";

interface RatingProps {
  rating: BeerRatingData;
}

const Rating = ({ rating: { id, author, comment } }: RatingProps) => (
  <em>
    <b>{author}</b>: {comment}
  </em>
);

interface BeerProps {
  beer: BeerData;
}
const Beer = ({ beer: { id, name, price, ratings } }: BeerProps) => (
  <div>
    <h3>{price}</h3>
    <h1>{name}</h1>
    {ratings.map(rating => <Rating key={rating.id} rating={rating} />)}
  </div>
);

interface BeerListProps {
  beers: BeerData[];
}
const BeerList = ({ beers }: BeerListProps) => <div>{beers.map(beer => <Beer key={beer.id} beer={beer} />)}</div>;

export default BeerList;
