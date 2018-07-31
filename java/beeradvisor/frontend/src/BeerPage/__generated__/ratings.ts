

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL fragment: ratings
// ====================================================

export interface ratingsResult_ratings {
  id: string;  // An immutable unique Id
}

export interface ratingsResult {
  id: string;                  // Unique, immutable Id, that identifies this Beer
  ratingsResult: ratingsResult_ratings[];  // All ratings for this Beer
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