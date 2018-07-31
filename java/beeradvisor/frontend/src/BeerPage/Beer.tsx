import * as React from "react";
import * as styles from "./Beer.scss";

import RatingForm from "./RatingForm";

import {
  BeerPageQueryResult_beer as BeerData,
  BeerPageQueryResult_beer_ratings as BeerRatingData
} from "./__generated__/BeerPageQuery";
import { AddRatingMutationResult, AddRatingMutationVariables } from "./__generated__/AddRatingMutation";
import { NewRating } from "../types";
import { ApolloClient, gql } from "apollo-boost";
import { Mutation } from "react-apollo";
interface RatingProps {
  rating: BeerRatingData;
}

const Rating = ({ rating: { id, author, comment } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author.name}</span>: <span className={styles.Comment}>„{comment}“</span>
  </div>
);

interface BeerProps {
  beer: BeerData;
}

const ADD_RATING_MUTATION = gql`
  mutation AddRatingMutation($input: AddRatingInput!) {
    addRating(ratingInput: $input) {
      id
      beer {
        id
      }
      author {
        name
      }
      comment
    }
  }
`;

class AddNewRatingMutation extends Mutation<AddRatingMutationResult, AddRatingMutationVariables> {}

export default function Beer({ beer: { id, name, price, ratings } }: BeerProps) {
  return (
    <div className={styles.Beer}>
      <div className={styles.DescriptionTitle}>
        <h1>{name}</h1>
        <h3>{price}</h3>
      </div>
      <div className={styles.Description}>
        <div className={styles.Img}>
          <img src={`/assets/beer/${id}.jpg`} />
        </div>
        <div>
          <div className={styles.Ratings}>
            <h1>What customers say:</h1>
            {ratings.map(rating => <Rating key={rating.id} rating={rating} />)}
          </div>
          <AddNewRatingMutation
            mutation={ADD_RATING_MUTATION}
            update={(cache, { data }) => {
              if (!data) {
                return;
              }

              const fragment = gql`
                fragment ratings on Beer {
                  id
                  ratings {
                    id
                  }
                }
              `;

              const cacheId = `Beer:${id}`;

              const result: any = cache.readFragment({
                id: cacheId,
                fragment
              });

              const newRatings = [...result.ratings, data.addRating];
              const newData = { ...result, ratings: newRatings };
              cache.writeFragment({
                id: cacheId,
                fragment,
                data: newData
              });
            }}
          >
            {addNewRating => {
              return (
                <RatingForm
                  beerId={id}
                  beerName={name}
                  onNewRating={({ comment, author }) => {
                    addNewRating({
                      variables: {
                        input: {
                          userId: "U1",
                          stars: 3,
                          comment,
                          beerId: id
                        }
                      }
                    });
                  }}
                />
              );
            }}
          </AddNewRatingMutation>
        </div>
      </div>
    </div>
  );
}
