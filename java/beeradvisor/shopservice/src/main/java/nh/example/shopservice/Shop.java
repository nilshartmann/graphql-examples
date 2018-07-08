package nh.example.shopservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Shop {
  private String id;
  private String name;
  private String street;
  private String postalCode;
  private String city;
  private String country;

  private List<String> beers;

  protected Shop() {
  }

  public Shop(String id, String name, String street, String postalCode, String city, String country, String... beerIds) {
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
