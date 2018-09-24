/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: OverviewPageQuery
// ====================================================

export interface OverviewPageQuery_beers {
  /**
   * Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   * The name of the beer
   */
  name: string;
  /**
   * Average Rating of this Beer
   */
  averageStars: number;
}

export interface OverviewPageQuery {
  /**
   * Returns all beers in our system
   */
  beers: OverviewPageQuery_beers[];
}
