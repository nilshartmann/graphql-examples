package nh.graphql.beeradvisor.login;

import nh.graphql.beeradvisor.user.User;

/**
 * LoginResponse
 */
public class LoginResponse {

  private final User user;
  private final String authToken;

  public LoginResponse(User user, String authToken) {
    this.user = user;
    this.authToken = authToken;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @return the authToken
   */
  public String getAuthToken() {
    return authToken;
  }

}