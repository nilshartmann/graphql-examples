package nh.graphql.beeradvisor.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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


  @Autowired
  private ShopRepository shopRepository;

  @PostConstruct
  @Transactional
  public void importDb() {
    logger.info("Importing Shop Database");

    try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shops.csv")))) {
      String line = null;
      int index = 1;
      while ((line = br.readLine()) != null) {
        String[] parts = line.trim().split("\\|");
        Shop shop = new Shop("S" + index++, parts[0], new Address(parts[1], parts[2], parts[3], parts[4]), parts[5].split(","));
        shopRepository.addShop(shop);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
