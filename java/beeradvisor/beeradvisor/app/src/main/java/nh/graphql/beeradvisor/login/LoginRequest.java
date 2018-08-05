package nh.graphql.beeradvisor.login;

/**
 * LoginRequest
 */
public class LoginRequest {

  private String userId;

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "[LoginRequest, userId: " + userId + "]";
  }

}