/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BeerPageQuery
// ====================================================

export interface BeerPageQuery_beer_ratings_beer {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
}

export interface BeerPageQuery_beer_ratings_author {
  __typename: "User";
  name: string;
}

export interface BeerPageQuery_beer_ratings {
  __typename: "Rating";
  /**
   *  An immutable unique Id
   */
  id: string;
  stars: number;
  /**
   *  The  beer, this Rating is written for
   */
  beer: BeerPageQuery_beer_ratings_beer;
  /**
   *  Who has written this Rating?
   */
  author: BeerPageQuery_beer_ratings_author;
  /**
   *  A comment for this beer
   */
  comment: string;
}

export interface BeerPageQuery_beer_shops {
  __typename: "Shop";
  /**
   *  Unique ID of this shop
   */
  id: string;
  /**
   *  The name of the shop
   */
  name: string;
}

export interface BeerPageQuery_beer {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   *  The name of the beer
   */
  name: string;
  /**
   *  The Beer's price
   */
  price: string;
  /**
   *  List of all Ratings for this Beer
   */
  ratings: BeerPageQuery_beer_ratings[];
  shops: BeerPageQuery_beer_shops[];
}

export interface BeerPageQuery {
  /**
   *  Returns a specific beer, identified by its id
   */
  beer: BeerPageQuery_beer | null;
}

export interface BeerPageQueryVariables {
  beerId: string;
}
