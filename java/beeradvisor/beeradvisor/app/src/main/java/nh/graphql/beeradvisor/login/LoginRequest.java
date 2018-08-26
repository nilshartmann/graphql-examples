package nh.graphql.beeradvisor.login;

import javax.validation.constraints.NotBlank;

/**
 * LoginRequest
 */
public class LoginRequest {

  @NotBlank
  private String login;

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * @param login the login to set
   */
  public void setLogin(String login) {
    this.login = login;
  }

  @Override
  public String toString() {
    return "[LoginRequest, login: " + login + "]";
  }

}