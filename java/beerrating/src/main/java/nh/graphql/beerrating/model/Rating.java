package nh.graphql.beerrating.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
public class Rating {

  @Id
  private  String id;
  private String author;
  private String comment;

  protected Rating() {

  }

  public Rating(String id, String author, String comment) {
    this.id = id;
    this.author = author;
    this.comment = comment;
  }

  public String getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getComment() {
    return comment;
  }
}
