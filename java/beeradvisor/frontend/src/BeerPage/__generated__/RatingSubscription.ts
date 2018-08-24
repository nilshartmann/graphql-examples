

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL subscription operation: RatingSubscription
// ====================================================

export interface RatingSubscriptionResult_rating_beer {
  id: string;  // Unique, immutable Id, that identifies this Beer
}

export interface RatingSubscriptionResult_rating_author {
  name: string;
}

export interface RatingSubscriptionResult_rating {
  id: string;                                // An immutable unique Id
  stars: number;
  beer: RatingSubscriptionResult_rating_beer;      // The  beer, this rating is written for
  author: RatingSubscriptionResult_rating_author;  // Who has written this rating?
  comment: string;                           // The rating itself
}

export interface RatingSubscriptionResult {
  rating: RatingSubscriptionResult_rating;
}

export interface RatingSubscriptionVariables {
  beerId: string;
}

//==============================================================
// START Enums and Input Objects
// All enums and input objects are included in every output file
// for now, but this will be changed soon.
// TODO: Link to issue to fix this.
//==============================================================

// 
interface AddRatingInput {
  beerId: string;
  userId: string;
  comment: string;
  stars: number;
}

//==============================================================
// END Enums and Input Objects
//==============================================================