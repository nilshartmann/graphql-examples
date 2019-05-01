package nh.graphql.beeradvisor.graphql.fetchers;

import graphql.schema.DataFetcher;
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
public class ShopFieldDataFetchers {
  private static Logger logger = LoggerFactory.getLogger(ShopFieldDataFetchers.class);

  @Autowired
  private BeerRepository beerRepository;

  public DataFetcher<List<Beer>> beersFetcher() {
      return environment -> {
          Shop shop = environment.getSource();
          final List<String> beerIds = shop.getBeers();
          return beerRepository.findWithIds(beerIds);
      };
  }

  public DataFetcher<AddressField> addressFetcher() {
      return environment -> {
          Shop shop = environment.getSource();
          return new AddressField(shop);
      };
  }
}
