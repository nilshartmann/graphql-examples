package nh.graphql.beeradvisor.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "user_")
public class User {

  @Id
  private String id;

  @NotNull
  private String name;

  private @OneToMany List<Rating> ratings;

  protected User() {
  }

  public User(String id, String name) {
    this.id = id;
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

  @JsonIgnore
  public List<Rating> getRatings() {
    return ratings;
  }
}
