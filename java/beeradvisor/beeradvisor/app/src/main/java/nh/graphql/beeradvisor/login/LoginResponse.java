package nh.graphql.beeradvisor.login;

/**
 * LoginResponse
 */
public class LoginResponse {

  private final String authToken;

  public LoginResponse(String authToken) {
    this.authToken = authToken;
  }

  /**
   * @return the authToken
   */
  public String getAuthToken() {
    return authToken;
  }

}