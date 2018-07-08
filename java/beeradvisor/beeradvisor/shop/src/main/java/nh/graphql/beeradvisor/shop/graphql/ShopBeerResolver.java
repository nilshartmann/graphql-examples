package nh.graphql.beeradvisor.shop.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.shop.Shop;
import nh.graphql.beeradvisor.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
