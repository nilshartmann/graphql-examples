import { RATINGS } from "./__dummy_data__";

export interface RatingDB {
  id: string;
  beerId: string;
  author: string;
  comment: string;
}

export class RatingStore {
  private ratings: RatingDB[];
  private idCount: number;
  constructor() {
    this.ratings = RATINGS.map(b => ({ ...b }));
    this.idCount = this.ratings.length;
  }

  all() {
    return this.ratings;
  }

  newRating(beerId: string, author: string, comment: string): RatingDB {
    const rating = {
      id: `R${++this.idCount}`,
      beerId,
      author,
      comment
    };

    this.ratings = [...this.ratings, rating];

    return rating;
  }
}
