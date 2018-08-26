package nh.graphql.plain;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Rating {

  private final String id;
  private final String author;
  private final String comment;

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
