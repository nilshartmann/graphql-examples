package nh.graphql.beeradvisor.login;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * LoginResponse
 */
public class LoginResponse {
  @NotBlank
  private final String userId;
  @NotBlank
  private final String username;
  @NotBlank
  private final String authToken;

  public LoginResponse(String userId, String username, String authToken) {
    this.userId = userId;
    this.username = username;
    this.authToken = authToken;
  }

  /**
   * @return the authToken
   */
  public String getAuthToken() {
    return authToken;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

}