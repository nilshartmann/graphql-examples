package nh.graphql.plain;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class RootResolver implements GraphQLQueryResolver {

  public List<Beer> beers() {
    return Arrays.asList(
        new Beer("B1", "Barfüßer", "5.60 EUR", new Rating("R1", "Nils", "jawoll!"), new Rating("R2", "Klaus", "naja...")),
        new Beer("B2", "Jever", "2.80 EUR", new Rating("R3", "Susi", "Skal!")));
  }

}
