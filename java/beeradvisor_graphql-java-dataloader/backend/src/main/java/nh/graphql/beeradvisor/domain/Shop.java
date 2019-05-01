package nh.graphql.beeradvisor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shop {
  private String id;
  private String name;
  private String street;
  private String postalCode;
  private String city;
  private String country;

  private List<String> beers;

  public Shop() {
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
