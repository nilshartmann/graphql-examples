package nh.graphql.beeradvisor.graphql.fetchers;

import nh.graphql.beeradvisor.domain.Shop;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddressField {
  private final Shop shop;

  public AddressField(Shop shop) {
    this.shop = shop;
  }

  public String getStreet() {
    return this.shop.getStreet();
  }

  public String getPostalCode() {
    return this.shop.getPostalCode();
  }

  public String getCity() {
    return this.shop.getCity();
  }

  public String getCountry() {
    return this.shop.getCountry();
  }
}
