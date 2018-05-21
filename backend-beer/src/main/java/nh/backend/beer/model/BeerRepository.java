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
      new Beer("B1", "Lingens", "120 NOK")

  };

  public List<Beer> findAll() {
    return Arrays.asList(BOOKS);
  }

}
