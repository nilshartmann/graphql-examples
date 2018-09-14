package nh.graphql.beeradvisor.rating.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.rating.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerFieldResolver implements GraphQLResolver<Beer> {
  private final Logger logger = LoggerFactory.getLogger(getClass());


  // does not work? https://github.com/graphql-java/graphql-java-tools/issues/171
  public List<Rating> ratingsWithStars(Beer beer, int starsInput) {
    logger.info(String.format("Finding Ratings with %s stars in Beer '%s'", starsInput, beer));
    return beer.getRatings().stream().filter(r -> r.getStars() == starsInput).collect(Collectors.toList());
  }
}
