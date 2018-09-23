package nh.graphql.beeradvisor.beerrating;

import nh.graphql.beeradvisor.beerrating.Rating;

/**
 * RatingCreatedEvent
 */
public class RatingCreatedEvent {

  private final Rating rating;

  public RatingCreatedEvent(Rating rating) {
    this.rating = rating;
  }

  /**
   * @return the beerrating
   */
  public Rating getRating() {
    return rating;
  }
}