import * as React from "react";

import { MutationFunction, MutationResult } from "@apollo/react-common";
import gql from "graphql-tag";
import {
  AddRatingMutation as AddRatingMutationResult,
  AddRatingMutation_addRating,
  AddRatingMutationVariables
} from "./querytypes/AddRatingMutation";
import { Mutation } from "@apollo/react-components";
import { DataProxy } from "apollo-cache";
import { RatingsFragment, RatingsFragment_ratings } from "./querytypes/RatingsFragment";

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
      stars
    }
  }
`;

const BEER_RATINGS_FRAGMENT = gql`
  fragment RatingsFragment on Beer {
    id
    ratings {
      id
    }
  }
`;

function mergeRatings(ratings: RatingsFragment_ratings[], newRating: AddRatingMutation_addRating) {
  if (ratings.find(r => r.id === newRating.id)) {
    // rating already contained in list
    return ratings;
  }

  return [...ratings, newRating];
}

function updateBeerCacheWithNewRating(cache: DataProxy, data: AddRatingMutationResult | undefined) {
  if (!data) {
    return;
  }

  const cacheId = `Beer:${data.addRating.beer.id}`;
  const existingBeerInCache = cache.readFragment<RatingsFragment>({
    id: cacheId,
    fragment: BEER_RATINGS_FRAGMENT
  });

  const newRatings = mergeRatings(existingBeerInCache!.ratings, data.addRating);
  cache.writeFragment({
    id: cacheId,
    fragment: BEER_RATINGS_FRAGMENT,
    data: { ...existingBeerInCache, ratings: newRatings }
  });
}

type AddRatingMutationProps = {
  children: (
    mutateFn: MutationFunction<AddRatingMutationResult, AddRatingMutationVariables>,
    result: MutationResult<AddRatingMutationResult>
  ) => JSX.Element | null;
  beerId: string;
};

export default function AddRating({ children }: AddRatingMutationProps) {
  return (
    <Mutation<AddRatingMutationResult, AddRatingMutationVariables>
      mutation={ADD_RATING_MUTATION}
      update={(cache, { data }) => updateBeerCacheWithNewRating(cache, data)}
    >
      {children}
    </Mutation>
  );
}
