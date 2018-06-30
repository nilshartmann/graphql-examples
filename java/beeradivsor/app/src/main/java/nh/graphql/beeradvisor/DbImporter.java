package nh.graphql.beeradvisor;

import nh.graphql.beeradvisor.rating.BeerRepository;
import nh.graphql.beeradvisor.rating.UserRepository;
import nh.graphql.beeradvisor.shop.Address;
import nh.graphql.beeradvisor.shop.Shop;
import nh.graphql.beeradvisor.rating.User;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.shop.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class DbImporter {

  private static final Logger logger = LoggerFactory.getLogger(DbImporter.class);

  @Autowired
  private BeerRepository beerRepository;

  @Autowired
  private ShopRepository shopRepository;

  @Autowired
  private UserRepository userRepository;

  @PostConstruct
  @Transactional
  public void importDb() {
    logger.info("Importing Database");


    final User U1 = userRepository.newUser("U1", "Waldemar Vasu");
    final User U2 = userRepository.newUser("U2", "Karl Marx");
    final User U3 = userRepository.newUser("U3", "Alessa Bradley");
    final User U4 = userRepository.newUser("U4", "Lauren Jones");
    final User U5 = userRepository.newUser("U5", "Nils");

    final Beer b1 = new Beer("B1", "Barfüßer", "3,80 EUR") //
        .addRating(U1, "R1", "Exceptional!", 4) //
        .addRating(U2, "R7", "Awwwesome!", 4) //
        .addRating(U3, "R9", "Can I order another please?", 5) //
        ;

    final Beer b2 = new Beer("B2", "Frydenlund", "150 NOK") //
        .addRating(U1, "R2", "Very good!", 4) //
        .addRating(U3, "R8", "phenomenal!", 5)
        .addRating(U4, "R15", "Delicate buttery flavor, with notes of sherry and old newsprint", 2)//
        ;

    final Beer b3 = new Beer("B3", "Grieskirchner", "3,20 EUR") //
        .addRating(U1, "R3", "Great taste!", 3) //
        .addRating(U5, "R10", "Tastes moreish", 4) //
        ;

    final Beer b4 = new Beer("B4", "Tuborg", "5,50 EUR") //
        .addRating(U5, "R4", "Try it, you'll love it!", 3) //
        .addRating(U4, "R11", "Hmmmm!!!!", 3) //
        ;

    final Beer b5 = new Beer("B5", "Baltic Tripple", "6,95 EUR") //
        .addRating(U2, "R5", "My favorite!", 4) //
        .addRating(U3, "R12", "Watery mouthfeel and long finish.", 3) //
        ;

    final Beer b6 = new Beer("B6", "Viktoria Bier", "4,20 EUR") //
        .addRating(U4, "R6", "Awwwesome!", 4) //
        .addRating(U2, "R13", "✊...", 5) //
        ;

    beerRepository.addBeer(b1);
    beerRepository.addBeer(b2);
    beerRepository.addBeer(b3);
    beerRepository.addBeer(b4);
    beerRepository.addBeer(b5);
    beerRepository.addBeer(b6);

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

  private static String[] randomBeerIds() {
    final List<String> allBeerIds = Arrays.asList("B1", "B2", "B3", "B4", "B5", "B6");
    Collections.shuffle(allBeerIds);

    int max = ThreadLocalRandom.current().nextInt(2, allBeerIds.size());

    return allBeerIds.subList(0, max).toArray(new String[0]);

  }

}
