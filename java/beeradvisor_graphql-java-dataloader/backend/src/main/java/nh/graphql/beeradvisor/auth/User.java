package nh.graphql.beeradvisor.auth;

import org.springframework.security.core.AuthenticatedPrincipal;

public class User implements AuthenticatedPrincipal {

  private String id;
  private String login;
  private String name;

  User() {
  }

    public User(String id, String login, String name) {
        this.id = id;
        this.login = login;
        this.name = name;
    }

    /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
  }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }
}
