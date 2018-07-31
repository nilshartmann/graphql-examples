

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL mutation operation: AddRatingMutation
// ====================================================

export interface AddRatingMutationResult_addRating_beer {
  id: string;  // Unique, immutable Id, that identifies this Beer
}

export interface AddRatingMutationResult_addRating_author {
  name: string;
}

export interface AddRatingMutationResult_addRating {
  id: string;                                  // An immutable unique Id
  beer: AddRatingMutationResult_addRating_beer;      // The  beer, this rating is written for
  author: AddRatingMutationResult_addRating_author;  // Who has written this rating?
  comment: string;                             // The rating itself
}

export interface AddRatingMutationResult {
  addRating: AddRatingMutationResult_addRating;  // Add a new Rating to a Beer and returns the new Rating
}

export interface AddRatingMutationVariables {
  input: AddRatingInput;
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