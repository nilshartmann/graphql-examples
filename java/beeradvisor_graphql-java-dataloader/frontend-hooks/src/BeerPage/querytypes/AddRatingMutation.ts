/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { AddRatingInput } from "./../../global-query-types";

// ====================================================
// GraphQL mutation operation: AddRatingMutation
// ====================================================

export interface AddRatingMutation_addRating_beer {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
}

export interface AddRatingMutation_addRating_author {
  __typename: "User";
  name: string;
}

export interface AddRatingMutation_addRating {
  __typename: "Rating";
  /**
   *  An immutable unique Id
   */
  id: string;
  /**
   *  The  beer, this Rating is written for
   */
  beer: AddRatingMutation_addRating_beer;
  /**
   *  Who has written this Rating?
   */
  author: AddRatingMutation_addRating_author;
  /**
   *  A comment for this beer
   */
  comment: string;
  stars: number;
}

export interface AddRatingMutation {
  /**
   *  Add a new Rating to a Beer and returns the new Rating
   */
  addRating: AddRatingMutation_addRating;
}

export interface AddRatingMutationVariables {
  input: AddRatingInput;
}
