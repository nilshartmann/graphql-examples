package nh.graphql.beeradvisor.beerrating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nh.graphql.beeradvisor.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "rating_")
public class Rating {
  private static Logger logger = LoggerFactory.getLogger(Rating.class);

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
  private User author;

  protected Rating() {

  }

  public Rating(Beer beer, User user, String id, String comment, int stars) {
    this.beer = beer;
    this.author = user;
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

  public User getAuthor() {
    logger.info("Retunring author " + author);
    return author;
  }

  public int getStars() {
    return stars;
  }
}
