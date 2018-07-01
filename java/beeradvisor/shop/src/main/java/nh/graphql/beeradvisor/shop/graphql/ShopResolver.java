package nh.graphql.beeradvisor.shop.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.rating.BeerRepository;
import nh.graphql.beeradvisor.shop.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ShopResolver implements GraphQLResolver<Shop> {
  private static Logger logger = LoggerFactory.getLogger(ShopResolver.class);

  @Autowired
  private BeerRepository beerRepository;

  public List<Beer> beers(Shop shop) {
    logger.info("Reading Beers for Shop " + shop.getId());
    final List<String> beerIds = shop.getBeers();
    logger.info("beers found " + beerIds);
    final List<Beer> beers = beerRepository.findWithIds(beerIds);
    logger.info("beers found " + beers);
    return beers;
  }

}
