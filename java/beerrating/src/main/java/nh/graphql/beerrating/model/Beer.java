package nh.graphql.beerrating.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
public class Beer {

  @Id
  private String id;

  @NotNull
  private String name;

  @NotNull
  private String price;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Rating> ratings = new LinkedList<>();

  protected Beer() {

  }

  public Beer(String id, String name, String price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Beer addRating(Author author, String ratingId, String comment, int stars) {
    Rating rating = new Rating(this, author, ratingId, comment, stars);
    this.ratings.add(rating);
    return this;
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

  public List<Rating> getRatings() {
    return ratings;
  }
}
