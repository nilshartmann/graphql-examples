const { makeExecutableSchema } = require("graphql-tools");

import { BeerDB, BeerStore } from "./BeerStore";
import { RatingDB, RatingStore } from "./RatingStore";
// try this out: https://github.com/prismagraphql/graphql-import

const typeDefs = `
"""Representation of a Beer that is in our Store"""
type Beer { 
  """Unique, immutable Id, that identifies this Beer"""
  id: ID!

  """The name of the beer"""
  name: String!

  """The Beer's price"""
  price: String!

  """All ratings for this Beer"""
  ratings: [Rating!]!
}

  type Rating {
    """An immutable unique Id"""
    id: ID!

    """The id of the beer, this rating is written for"""
    beerId: ID!

    """Who has written this rating?"""
    author: String!

    """The rating itself"""
    comment: String!
  }

  type ProcessInfo {
    name: String!
    nodeJsVersion: String!
    uptime: String!
  }


  type Query {
    """Returns all beers in our store"""
    beers: [Beer!]!

    """Returns the Beer with the specified Id"""
    beer(beerId: String): Beer
  
    """All ratings stored in our system"""
    ratings: [Rating!]! 
    

    """Returns health information about the running process"""
    ping: ProcessInfo!
  }

  input AddRatingInput {
    beerId: String!
    author: String!
    comment: String!
  }

  type Mutation {
    """Add a new Rating to a Beer and returns the new Rating"""
    addRating(ratingInput: AddRatingInput): Rating!
  }
`;

type Rating = RatingDB;

interface Beer extends BeerDB {
  ratings: Rating[];
}

interface AddRatingInput {
  beerId: string;
  author: string;
  comment: string;
}

const createBeerRatingSchema = (port: number) => {
  const bootTime = Date.now();

  const beerStore = new BeerStore();
  const ratingStore = new RatingStore();

  // The resolvers
  const resolvers = {
    Query: {
      beers: (): BeerDB[] => beerStore.all(),
      beer: (_: any, { beerId }: { beerId: string }) => beerStore.all().find(beer => beer.id === beerId),
      ratings: (): RatingDB[] => ratingStore.all(),
      ping: () => ({
        name: "Beer Rating Backend",
        nodeJsVersion: process.versions.node,
        uptime: `${(Date.now() - bootTime) / 1000}s`
      })
    },

    Beer: {
      // we're using an own 'ratings' resolver as we only want to load the
      // ratings if they are really queried
      ratings: (parent: BeerDB): Rating[] => {
        console.log(`Loading Ratings for Beer '${parent.id}'`);
        return ratingStore.all().filter(rating => rating.beerId === parent.id);
      }
    },

    Mutation: {
      addRating: (_: any, { ratingInput }: { ratingInput: AddRatingInput }): Rating => {
        const { beerId, author, comment } = ratingInput;
        return ratingStore.newRating(beerId, author, comment);
      }
    }
  };

  // Put together a schema
  return makeExecutableSchema({
    typeDefs,
    resolvers
  });
};

export default createBeerRatingSchema;
