package nh.graphql.beeradvisor.domain;

/**
 * RatingCreatedEvent
 */
public class RatingCreatedEvent {

  private final Rating rating;

  public RatingCreatedEvent(Rating rating) {
    this.rating = rating;
  }

  /**
   * @return the domain
   */
  public Rating getRating() {
    return rating;
  }
}