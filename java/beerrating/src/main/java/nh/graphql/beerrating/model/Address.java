package nh.graphql.beerrating.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Embeddable
public class Address {

  @NotNull
  private String street;
  @NotNull
  private String postalCode;
  @NotNull
  private String city;
  @NotNull
  private String country;

  protected Address() {
  }

  public Address(@NotNull String street, @NotNull String postalCode, @NotNull String city, @NotNull String country) {
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
