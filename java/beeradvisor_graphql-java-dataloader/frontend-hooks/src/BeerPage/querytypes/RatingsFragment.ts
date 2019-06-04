/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL fragment: RatingsFragment
// ====================================================

export interface RatingsFragment_ratings {
  __typename: "Rating";
  /**
   *  An immutable unique Id
   */
  id: string;
}

export interface RatingsFragment {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   *  List of all Ratings for this Beer
   */
  ratings: RatingsFragment_ratings[];
}
