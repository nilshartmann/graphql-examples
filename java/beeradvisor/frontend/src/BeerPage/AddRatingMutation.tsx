import * as React from "react";

import { AddRatingMutationResult, AddRatingMutationVariables } from "./__generated__/AddRatingMutation";
import { gql } from "apollo-boost";
import { Mutation, MutationFn, MutationResult } from "react-apollo";

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

interface AddRatingMutationProps {
  children: (
    mutateFn: MutationFn<AddRatingMutationResult, AddRatingMutationVariables>,
    result: MutationResult<AddRatingMutationResult>
  ) => React.ReactNode;
  beerId: string;
}

export default function AddRatingMutation({ beerId, children }: AddRatingMutationProps) {
  return (
    <Mutation<AddRatingMutationResult, AddRatingMutationVariables>
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

        const cacheId = `Beer:${beerId}`;

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
      {children}
    </Mutation>
  );
}
