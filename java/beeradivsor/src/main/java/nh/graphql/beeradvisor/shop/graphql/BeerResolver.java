package nh.graphql.beeradvisor.shop.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.shop.Shop;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Component
public class BeerResolver implements GraphQLResolver<Beer> {

  public Shop shops(Beer beer) {
    return null;
  }
}
