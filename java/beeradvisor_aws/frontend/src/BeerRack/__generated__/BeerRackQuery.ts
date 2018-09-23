/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerRackQuery
// ====================================================

export interface BeerRackQuery_beers {
  /**
   * Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   * The name of the beer
   */
  name: string;
}

export interface BeerRackQuery {
  /**
   * Returns all beers in our system
   */
  beers: BeerRackQuery_beers[];
}
