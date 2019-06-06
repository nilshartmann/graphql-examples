/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL mutation operation: UpdateBeerNameMutation
// ====================================================

export interface UpdateBeerNameMutation_updatedBeer {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   *  The name of the beer
   */
  name: string;
}

export interface UpdateBeerNameMutation {
  updatedBeer: UpdateBeerNameMutation_updatedBeer;
}

export interface UpdateBeerNameMutationVariables {
  beerId: string;
  newName: string;
}
