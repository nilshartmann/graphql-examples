

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerRatingAppQuery
// ====================================================

export interface BeerRatingAppQueryResult_beerServiceStatus {
  name: string;
  uptime: string;
  javaVersion: string;
}

export interface BeerRatingAppQueryResult_ratingServiceStatus {
  name: string;
  nodeJsVersion: string;
  uptime: string;
}

export interface BeerRatingAppQueryResult_beers_ratings {
  id: string;       // An immutable unique Id
  beerId: string;   // The id of the beer, this rating is written for
  author: string;   // Who has written this rating?
  comment: string;  // The rating itself
}

export interface BeerRatingAppQueryResult_beers {
  id: string;                                   // Unique, immutable Id, that identifies this Beer
  name: string;                                 // The name of the beer
  price: string;                                // The Beer's price
  ratings: BeerRatingAppQueryResult_beers_ratings[];  // All Ratings for this Beer
}

export interface BeerRatingAppQueryResult {
  beerServiceStatus: BeerRatingAppQueryResult_beerServiceStatus;      // Returns some information about the running **Beer** application
  ratingServiceStatus: BeerRatingAppQueryResult_ratingServiceStatus;  // Returns health information about the running **Rating** process
  beers: BeerRatingAppQueryResult_beers[];                            // Returns all beers in our store
}

//==============================================================
// START Enums and Input Objects
// All enums and input objects are included in every output file
// for now, but this will be changed soon.
// TODO: Link to issue to fix this.
//==============================================================

//==============================================================
// END Enums and Input Objects
//==============================================================