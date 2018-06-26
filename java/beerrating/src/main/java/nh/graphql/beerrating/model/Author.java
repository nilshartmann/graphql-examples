package nh.graphql.beerrating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
public class Author {

  @Id
  private String id;

  @NotNull
  private String name;

  private @OneToMany(fetch = FetchType.EAGER)
  List<Rating> ratings;

  protected Author() {
  }

  public Author(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @JsonIgnore
  public List<Rating> getRatings() {
    return ratings;
  }
}
