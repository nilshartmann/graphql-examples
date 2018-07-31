

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerRatingAppQuery
// ====================================================

export interface BeerRatingAppQueryResult_beers {
  id: string;  // Unique, immutable Id, that identifies this Beer
  hasDraftRating: boolean;
}

export interface BeerRatingAppQueryResult {
  beers: BeerRatingAppQueryResult_beers[];  // Returns all beers in our system
  currentBeerId: string;
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