package nh.graphql.beeradvisor.auth.graphql;

/**
 * Authentication
 */
public class Authentication {
  private final String userId;
  private final String username;
  private final String authToken;

  public Authentication(String userId, String username, String authToken) {
    this.userId = userId;
    this.username = username;
    this.authToken = authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUsername() {
    return username;
  }

  public String getUserId() {
    return userId;
  }

}