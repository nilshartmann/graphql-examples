package nh.graphql.beeradvisor.auth.graphql;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LoginResponse {

  private final Authentication authentication;
  private final String error;

  private LoginResponse(Authentication authentication, String error) {
    this.authentication = authentication;
    this.error = error;
  }

  public static LoginResponse succeeded(Authentication authentication) {
    return new LoginResponse(authentication, null);
  }

  public static LoginResponse failed(String error) {
    return new LoginResponse(null, error);
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public String getError() {
    return error;
  }
}
