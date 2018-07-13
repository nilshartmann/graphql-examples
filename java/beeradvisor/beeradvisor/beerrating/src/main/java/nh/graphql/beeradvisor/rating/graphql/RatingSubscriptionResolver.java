package nh.graphql.beeradvisor.rating.graphql;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nh.graphql.beeradvisor.rating.Rating;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RatingSubscriptionResolver implements GraphQLSubscriptionResolver {

  @Autowired
  private Publisher<Rating> ratingPublisher;

  public Publisher<Rating> onNewRating() {
    return this.ratingPublisher;
  }
}
