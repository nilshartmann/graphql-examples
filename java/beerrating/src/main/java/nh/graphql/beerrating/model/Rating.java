package nh.graphql.beerrating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name="rating_")
public class Rating {

  @Id
  private String id;

  @NotNull
  private String comment;

  @NotNull
  private int stars;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Beer beer;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  protected Rating() {

  }

  public Rating(Beer beer, User user, String id, String comment, int stars) {
    this.beer = beer;
    this.user = user;
    this.id = id;
    this.comment = comment;
    this.stars = stars;
  }

  public String getId() {
    return id;
  }

  public String getComment() {
    return comment;
  }

  public Beer getBeer() {
    return beer;
  }

  public User getUser() {
    return user;
  }

  public int getStars() {
    return stars;
  }
}
