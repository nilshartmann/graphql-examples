package nh.graphql.beeradvisor.rating.graphql;

import nh.graphql.beeradvisor.rating.Rating;

/**
 * RatingCreatedEvent
 */
public class RatingCreatedEvent {

  private final Rating rating;

  public RatingCreatedEvent(Rating rating) {
    this.rating = rating;
  }

  /**
   * @return the rating
   */
  public Rating getRating() {
    return rating;
  }
}