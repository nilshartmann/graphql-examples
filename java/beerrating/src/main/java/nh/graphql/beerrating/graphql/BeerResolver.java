package nh.graphql.beerrating.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beerrating.model.Beer;
import nh.graphql.beerrating.model.Shop;
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
