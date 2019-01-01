package nh.graphql.beeradvisor.graphql.query;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.BeerRepository;
import nh.graphql.beeradvisor.domain.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ShopFieldResolver implements GraphQLResolver<Shop> {
  private static Logger logger = LoggerFactory.getLogger(ShopFieldResolver.class);

  @Autowired
  private BeerRepository beerRepository;

  public List<Beer> beers(Shop shop) {
    final List<String> beerIds = shop.getBeers();
    return beerRepository.findWithIds(beerIds);
  }

  public AddressField address(Shop shop) {
    return new AddressField(shop);
  }
}
