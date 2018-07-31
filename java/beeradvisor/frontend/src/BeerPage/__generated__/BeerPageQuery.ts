

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerPageQuery
// ====================================================

export interface BeerPageQueryResult_beer_ratings_beer {
  id: string;  // Unique, immutable Id, that identifies this Beer
}

export interface BeerPageQueryResult_beer_ratings_author {
  name: string;
}

export interface BeerPageQueryResult_beer_ratings {
  id: string;                                 // An immutable unique Id
  beer: BeerPageQueryResult_beer_ratings_beer;      // The  beer, this rating is written for
  author: BeerPageQueryResult_beer_ratings_author;  // Who has written this rating?
  comment: string;                            // The rating itself
}

export interface BeerPageQueryResult_beer {
  id: string;                             // Unique, immutable Id, that identifies this Beer
  name: string;                           // The name of the beer
  price: string;                          // The Beer's price
  ratings: BeerPageQueryResult_beer_ratings[];  // List of all Ratings for this Beer
}

export interface BeerPageQueryResult {
  beer: BeerPageQueryResult_beer | null;  // Returns a specific beer, identified by its id
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
  userId: string;
  comment: string;
  stars: number;
}

//==============================================================
// END Enums and Input Objects
//==============================================================