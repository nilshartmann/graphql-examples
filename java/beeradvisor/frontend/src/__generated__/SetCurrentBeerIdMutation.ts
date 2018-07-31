

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL mutation operation: SetCurrentBeerIdMutation
// ====================================================

export interface SetCurrentBeerIdMutationResult {
  setCurrentBeerId: string;
}

export interface SetCurrentBeerIdMutationVariables {
  newBeerId: string;
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