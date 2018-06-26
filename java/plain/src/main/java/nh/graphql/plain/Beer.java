package nh.graphql.plain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Beer {

  private final String id;
  private final String name;
  private final String price;

  private final List<Rating> ratings = new LinkedList<>();

  public Beer(String id, String name, String price, Rating... ratings) {
    this.id = id;
    this.name = name;
    this.price = price;

    this.ratings.addAll(Arrays.asList(ratings));
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPrice() {
    return price;
  }
}
