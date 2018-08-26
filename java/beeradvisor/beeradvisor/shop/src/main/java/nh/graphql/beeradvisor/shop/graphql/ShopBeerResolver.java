package nh.graphql.beeradvisor.shop.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.rating.Rating;
import nh.graphql.beeradvisor.shop.Shop;
import nh.graphql.beeradvisor.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Component
public class ShopBeerResolver implements GraphQLResolver<Beer> {

  @Autowired
  private ShopService shopService;

  public List<Shop> shops(Beer beer) {
    final String beerId = beer.getId();

    return shopService.findShopsForBeer(beerId);
  }

  // this should be in BeerFieldResolver but does not work due to graphl-java-tools bug: https://github.com/graphql-java/graphql-java-tools/issues/171
  public List<Rating> ratingsWithStars(Beer beer, int starsInput) {
    return beer.getRatings().stream().filter(r -> r.getStars() == starsInput).collect(Collectors.toList());
  }
}
