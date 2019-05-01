package nh.graphql.beeradvisor.domain;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddRatingInput {

  private String beerId;
  private String userId;
  private String comment;
  private int stars;

  public String getBeerId() {
    return beerId;
  }

  public void setBeerId(String beerId) {
    this.beerId = beerId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getStars() {
    return stars;
  }

  public void setStars(int stars) {
    this.stars = stars;
  }
}
