package nh.graphql.beerrating;

import nh.graphql.beerrating.model.User;
import nh.graphql.beerrating.model.Beer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class DbImporter {

  private static final Logger logger = LoggerFactory.getLogger(DbImporter.class);

  @Autowired
  private BeerRepository beerRepository;

  @Autowired
  private UserRepository userRepository;

  @PostConstruct
  @Transactional
  public void importDb() {
    logger.info("Importing Database");

    User klaus = userRepository.newUser("U1", "Klaus");
    User susi = userRepository.newUser("U2", "Susi");
    User alessa = userRepository.newUser("U3", "Alessa Bradley");
    User lauren = userRepository.newUser("U4", "Lauren Jones");

    Beer b1 = new Beer("B1", "Barfüßer", "5.60 EUR") //
        .addRating(klaus, "R1", "jawoll!", 3) //
        .addRating(susi, "R2", "naja...", 2);
    Beer b2 = new Beer("B2", "Jever", "2.80 EUR").addRating(alessa, "R3", "Skal", 5);

    beerRepository.addBeer(b1);
    beerRepository.addBeer(b2);


  }

}
