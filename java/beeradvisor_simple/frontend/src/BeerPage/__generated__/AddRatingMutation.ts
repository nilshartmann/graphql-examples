/* tslint:disable */
// This file was automatically generated and should not be edited.

import { AddRatingInput } from "./../../__generated__/query-types";

// ====================================================
// GraphQL mutation operation: AddRatingMutation
// ====================================================

export interface AddRatingMutation_addRating_beer {
  /**
   * Unique, immutable Id, that identifies this Beer
   */
  id: string;
}

export interface AddRatingMutation_addRating_author {
  name: string;
}

export interface AddRatingMutation_addRating {
  /**
   * An immutable unique Id
   */
  id: string;
  /**
   * The  beer, this domain is written for
   */
  beer: AddRatingMutation_addRating_beer;
  /**
   * Who has written this domain?
   */
  author: AddRatingMutation_addRating_author;
  /**
   * The domain itself
   */
  comment: string;
  stars: number;
}

export interface AddRatingMutation {
  /**
   * Add a new Rating to a Beer and returns the new Rating
   */
  addRating: AddRatingMutation_addRating;
}

export interface AddRatingMutationVariables {
  input: AddRatingInput;
}
