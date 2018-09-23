package nh.graphql.beeradvisor.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ShopDbImporter {

  private static final Logger logger = LoggerFactory.getLogger(ShopDbImporter.class);

  private final ShopRepository shopRepository;

  public ShopDbImporter(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }


  @PostConstruct
  public void importDb() {
    logger.info("Importing Shop Database");

    if (shopRepository.findAll().size() > 0) {
      logger.info("Shops already imported");
      return;
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shops.csv")))) {
      String line = null;
      int index = 1;
      while ((line = br.readLine()) != null) {
        String[] parts = line.trim().split("\\|");
        Shop shop = new Shop("S" + index++, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5].split(","));
        shopRepository.addShop(shop);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
