/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL fragment: ratings
// ====================================================

export interface ratings_ratings {
  /**
   * An immutable unique Id
   */
  id: string;
}

export interface ratings {
  /**
   * Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   * List of all Ratings for this Beer
   */
  ratings: ratings_ratings[];
}
