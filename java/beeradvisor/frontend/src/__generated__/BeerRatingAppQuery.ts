

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerRatingAppQuery
// ====================================================

export interface BeerRatingAppQueryResult_beers_ratings_author {
  id: string;
  name: string;
}

export interface BeerRatingAppQueryResult_beers_ratings {
  id: string;                                       // An immutable unique Id
  author: BeerRatingAppQueryResult_beers_ratings_author;  // Who has written this rating?
  comment: string;                                  // The rating itself
}

export interface BeerRatingAppQueryResult_beers {
  id: string;                                   // Unique, immutable Id, that identifies this Beer
  price: string;                                // The Beer's price
  name: string;                                 // The name of the beer
  ratings: BeerRatingAppQueryResult_beers_ratings[];  // List of all Ratings for this Beer
}

export interface BeerRatingAppQueryResult {
  beers: BeerRatingAppQueryResult_beers[];  // Returns all beers in our system
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