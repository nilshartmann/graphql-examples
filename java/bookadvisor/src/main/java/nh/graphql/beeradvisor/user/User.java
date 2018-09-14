package nh.graphql.beeradvisor.user;

import org.springframework.security.core.AuthenticatedPrincipal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "user_")
public class User implements AuthenticatedPrincipal {

  @Id
  private String id;

  @NotNull
  @Column(unique = true)
  private String login;

  @NotNull
  private String name;

  protected User() {
  }

  public User(String id, String login, String name) {
    this.id = id;
    this.name = name;
    this.login = login;
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
}
