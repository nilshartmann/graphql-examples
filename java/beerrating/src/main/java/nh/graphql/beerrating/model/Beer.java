package nh.graphql.beerrating.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
public class Beer {

  @Id
  private String id;
  private String name;
  private String price;

  @OneToMany
  private List<Rating> ratings = new LinkedList<>();

  protected Beer() {

  }

  public Beer(String id, String name, String price, Rating... ratings) {
    this.id = id;
    this.name = name;
    this.price = price;

    this.ratings = new LinkedList<>(Arrays.asList(ratings));
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
