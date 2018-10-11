package nh.graphql.beeradvisor.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.BeerRepository;
import nh.graphql.beeradvisor.domain.Shop;
import nh.graphql.beeradvisor.domain.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerAdvisorQueryResolver implements GraphQLQueryResolver {

  private final BeerRepository beerRepository;
  private final ShopRepository shopRepository;

  @Autowired
  public BeerAdvisorQueryResolver(BeerRepository beerRepository, ShopRepository shopRepository) {
    this.beerRepository = beerRepository;
    this.shopRepository = shopRepository;
  }

  public Beer beer(String beerId) {
    return beerRepository.getBeer(beerId);
  }

  public List<Beer> beers() {
    return beerRepository.findAll();
  }

  public Shop shop(String shopId) {
    return shopRepository.findShop(shopId);
  }

  public List<Shop> shops() {
    return shopRepository.findAll();
  }
}
