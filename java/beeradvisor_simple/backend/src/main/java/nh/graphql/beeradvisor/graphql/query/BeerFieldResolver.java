package nh.graphql.beeradvisor.graphql.query;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.Rating;
import nh.graphql.beeradvisor.domain.Shop;
import nh.graphql.beeradvisor.domain.ShopRepository;
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

  private final ShopRepository shopRepository;

  public BeerFieldResolver(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  public List<Shop> shops(Beer beer) {
    final String beerId = beer.getId();
    return shopRepository.findShopsWithBeer(beerId);
  }

  public int averageStars(Beer beer) {
    return (int) Math.round(beer.getRatings().stream().mapToDouble(Rating::getStars).average().getAsDouble());
  }

  public List<Rating> ratingsWithStars(Beer beer, int starsInput) {
    logger.info(String.format("Finding Ratings with %s stars in Beer '%s'", starsInput, beer));
    return beer.getRatings().stream().filter(r -> r.getStars() == starsInput).collect(Collectors.toList());
  }
}
