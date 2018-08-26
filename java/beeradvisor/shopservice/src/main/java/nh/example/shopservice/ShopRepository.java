package nh.example.shopservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class ShopRepository {

  private static Logger logger = LoggerFactory.getLogger(ShopRepository.class);

  private final List<Shop> shops = new LinkedList<>();

  public void addShop(Shop shop) {
    shops.add(shop);
  }

  public List<Shop> findAll() {
    return Collections.unmodifiableList(this.shops);
  }

  public Optional<Shop> findById(String shopId) {
    for (Shop shop : shops) {
      if (shop.getId().equals((shopId))) {
        return Optional.of(shop);
      }
    }

    return Optional.empty();
  }
}
