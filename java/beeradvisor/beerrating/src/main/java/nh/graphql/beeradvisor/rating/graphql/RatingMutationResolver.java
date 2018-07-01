package nh.graphql.beeradvisor.rating.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import nh.graphql.beeradvisor.rating.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RatingMutationResolver implements GraphQLMutationResolver {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BeerRepository beerRepository;

  public Rating addRating(AddRatingInput addRatingInput) {
    User user = userRepository.getUser(addRatingInput.getUserId());
    Beer beer = beerRepository.getBeer(addRatingInput.getBeerId());

    Rating rating = beer.addRating(user, addRatingInput.getComment(), addRatingInput.getStars());
    beerRepository.saveBeer(beer);

    return rating;
  }
}
