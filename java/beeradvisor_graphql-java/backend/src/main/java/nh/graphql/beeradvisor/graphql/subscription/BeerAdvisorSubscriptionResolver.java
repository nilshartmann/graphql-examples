package nh.graphql.beeradvisor.graphql.subscription;

import nh.graphql.beeradvisor.domain.Rating;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerAdvisorSubscriptionResolver {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final RatingPublisher ratingPublisher;

  @Autowired
  public BeerAdvisorSubscriptionResolver(RatingPublisher ratingPublisher) {
    this.ratingPublisher = ratingPublisher;
  }

  public Publisher<Rating> onNewRating() {
    return this.ratingPublisher.getPublisher();
  }

  public Publisher<Rating> newRatings(String beerId) {
    logger.info("Subscription for 'newRatings' (" + beerId + ") received");
    return this.ratingPublisher.getPublisher(beerId);
  }
}
