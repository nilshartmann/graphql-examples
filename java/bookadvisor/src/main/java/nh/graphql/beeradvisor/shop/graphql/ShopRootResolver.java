package nh.graphql.beeradvisor.shop.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nh.graphql.beeradvisor.shop.Shop;
import nh.graphql.beeradvisor.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ShopRootResolver implements GraphQLQueryResolver {
  private static Logger logger = LoggerFactory.getLogger(ShopRootResolver.class);


  @Autowired
  private ShopService shopService;

  public Shop shop(String shopId) {
    logger.info("Read Shop by Id " + shopId);
    return shopService.findShop(shopId);
  }

  public List<Shop> shops() {
    return shopService.getShops();
  }
}
