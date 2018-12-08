package nh.graphql.beeradvisor.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
public class Shop {
  @Id
  private String id;

  @NotNull
  private String name;
  @NotNull
  private String street;
  @NotNull
  private String postalCode;
  @NotNull
  private String city;
  @NotNull
  private String country;

  @ElementCollection
  private List<String> beers;

  // hell
  protected Shop() {
  }

  public Shop(String id, String name, String street, String postalCode, String city, String country,
      String... beerIds) {
    this.id = id;
    this.name = name;
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
    this.beers = Arrays.asList(beerIds).stream().map(String::trim).collect(toList());
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getStreet() {
    return street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public List<String> getBeers() {
    return beers;
  }
}
