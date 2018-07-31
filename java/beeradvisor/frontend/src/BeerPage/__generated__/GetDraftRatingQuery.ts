

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: GetDraftRatingQuery
// ====================================================

export interface GetDraftRatingQueryResult_draft {
  id: string;  // Must match beerId in Beer
  author: string;
  comment: string;
}

export interface GetDraftRatingQueryResult {
  draft: GetDraftRatingQueryResult_draft | null;
}

export interface GetDraftRatingQueryVariables {
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