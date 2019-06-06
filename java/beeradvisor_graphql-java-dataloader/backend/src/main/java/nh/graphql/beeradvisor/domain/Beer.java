package nh.graphql.beeradvisor.domain;

import nh.graphql.beeradvisor.auth.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "beer_")
public class Beer {

  @Id
  private String id;

  @NotNull
  private String name;

  @NotNull
  private String price;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Rating> ratings = new LinkedList<>();

  protected Beer() {

  }

  public Beer(String id, String name, String price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Beer addRating(String userId, String ratingId, String comment, int stars) {
    Rating rating = new Rating(this, userId, ratingId, comment, stars);
    this.ratings.add(rating);
    return this;
  }

  public Rating addRating(String userId,String comment, int stars) {
    Rating rating = new Rating(this, userId, UUID.randomUUID().toString(), comment, stars);
    this.ratings.add(rating);
    return rating;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
    return price;
  }

  public List<Rating> getRatings() {
    return ratings;
  }
}
