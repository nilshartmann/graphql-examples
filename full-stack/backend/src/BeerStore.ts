import { BEERS } from "./__dummy_data__";

export interface BeerDB {
  id: string;
  name: string;
  price: string;
}

export class BeerStore {
  private beers: BeerDB[];
  constructor() {
    this.beers = BEERS.map(b => ({ ...b }));
  }

  all() {
    return this.beers;
  }
}
