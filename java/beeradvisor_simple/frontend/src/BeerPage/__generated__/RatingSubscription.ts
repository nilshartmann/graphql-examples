/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL subscription operation: RatingSubscription
// ====================================================

export interface RatingSubscription_rating_beer {
  /**
   * Unique, immutable Id, that identifies this Beer
   */
  id: string;
}

export interface RatingSubscription_rating_author {
  name: string;
}

export interface RatingSubscription_rating {
  /**
   * An immutable unique Id
   */
  id: string;
  stars: number;
  /**
   * The  beer, this domain is written for
   */
  beer: RatingSubscription_rating_beer;
  /**
   * Who has written this domain?
   */
  author: RatingSubscription_rating_author;
  /**
   * The domain itself
   */
  comment: string;
}

export interface RatingSubscription {
  rating: RatingSubscription_rating;
}

export interface RatingSubscriptionVariables {
  beerId: string;
}
