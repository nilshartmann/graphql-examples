package nh.backend.beer.model;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class BeerRepository {

  private final static Beer[] BOOKS = {
      new Beer("B1", "Barfüßer", "3,80 EUR"),
      new Beer("B2", "Frydenlund", "150 NOK"),
      new Beer("B3", "Grieskirchner", "3,20 EUR"),
      new Beer("B4", "Tuborg", "5,50 EUR"),
      new Beer("B5", "Baltic Tripple", "6,95 EUR"),
      new Beer("B6", "Viktoria Bier", "4,20 EUR"),

  };

  public List<Beer> findAll() {
    return Arrays.asList(BOOKS);
  }

}
