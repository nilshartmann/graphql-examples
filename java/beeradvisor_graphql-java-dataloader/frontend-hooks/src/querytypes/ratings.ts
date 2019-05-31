/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL fragment: ratings
// ====================================================

export interface ratings_ratings {
  __typename: "Rating";
  /**
   *  An immutable unique Id
   */
  id: string;
}

export interface ratings {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   *  List of all Ratings for this Beer
   */
  ratings: ratings_ratings[];
}
