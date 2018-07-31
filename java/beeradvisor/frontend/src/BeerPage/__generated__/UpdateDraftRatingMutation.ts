

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL mutation operation: UpdateDraftRatingMutation
// ====================================================

export interface UpdateDraftRatingMutationResult_setDraftRatingForBeer {
  author: string;
  comment: string;
}

export interface UpdateDraftRatingMutationResult {
  setDraftRatingForBeer: UpdateDraftRatingMutationResult_setDraftRatingForBeer;
}

export interface UpdateDraftRatingMutationVariables {
  beerId: string;
  author: string;
  comment: string;
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