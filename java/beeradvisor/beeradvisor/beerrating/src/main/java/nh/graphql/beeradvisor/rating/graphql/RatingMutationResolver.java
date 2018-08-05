package nh.graphql.beeradvisor.rating.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import nh.graphql.beeradvisor.rating.*;
import nh.graphql.beeradvisor.user.User;
import nh.graphql.beeradvisor.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RatingMutationResolver implements GraphQLMutationResolver {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private BeerRepository beerRepository;

  @Transactional
  public Rating addRating(AddRatingInput addRatingInput) {
    User user = userRepository.getUser(addRatingInput.getUserId());
    Beer beer = beerRepository.getBeer(addRatingInput.getBeerId());

    Rating rating = beer.addRating(user, addRatingInput.getComment(), addRatingInput.getStars());
    beerRepository.saveBeer(beer);

    applicationEventPublisher.publishEvent(new RatingCreatedEvent(rating));

    return rating;
  }
}
