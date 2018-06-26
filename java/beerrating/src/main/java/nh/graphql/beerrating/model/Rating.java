package nh.graphql.beerrating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
public class Rating {

  @Id
  private  String id;

  @NotNull
  private String comment;

  @NotNull
  private int stars;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private Beer beer;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  private Author author;

  protected Rating() {

  }

  public Rating(Beer beer, Author author, String id, String comment, int stars) {
    this.beer = beer;
    this.author = author;
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

  public Author getAuthor() {
    return author;
  }

  public int getStars() {
    return stars;
  }
}
