

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerPageQuery
// ====================================================

export interface BeerPageQueryResult_beer_ratings {
  id: string;       // An immutable unique Id
  beerId: string;   // The id of the beer, this rating is written for
  author: string;   // Who has written this rating?
  comment: string;  // The rating itself
}

export interface BeerPageQueryResult_beer {
  id: string;                             // Unique, immutable Id, that identifies this Beer
  name: string;                           // The name of the beer
  price: string;                          // The Beer's price
  ratings: BeerPageQueryResult_beer_ratings[];  // All ratings for this Beer
}

export interface BeerPageQueryResult_draftRating {
  id: string;  // Must match beerId in Beer
  author: string;
  comment: string;
}

export interface BeerPageQueryResult {
  beer: BeerPageQueryResult_beer | null;  // Returns the Beer with the specified Id
  draftRating: BeerPageQueryResult_draftRating | null;
}

export interface BeerPageQueryVariables {
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
  author: string;
  comment: string;
}

//==============================================================
// END Enums and Input Objects
//==============================================================