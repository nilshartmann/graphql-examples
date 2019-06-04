/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL subscription operation: RatingSubscription
// ====================================================

export interface RatingSubscription_rating_beer {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
}

export interface RatingSubscription_rating_author {
  __typename: "User";
  name: string;
}

export interface RatingSubscription_rating {
  __typename: "Rating";
  /**
   *  An immutable unique Id
   */
  id: string;
  stars: number;
  /**
   *  The  beer, this Rating is written for
   */
  beer: RatingSubscription_rating_beer;
  /**
   *  Who has written this Rating?
   */
  author: RatingSubscription_rating_author;
  /**
   *  A comment for this beer
   */
  comment: string;
}

export interface RatingSubscription {
  rating: RatingSubscription_rating;
}

export interface RatingSubscriptionVariables {
  beerId: string;
}
