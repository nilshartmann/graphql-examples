package nh.graphql.beeradvisor.shop;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "shop_")
public class Shop {
  @Id
  private String id;

  private String name;

  @Embedded
  private Address address;

  @ElementCollection
  private List<String> beers;

  protected Shop() {
  }

  public Shop(String id, String name, Address address, String... beerIds) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.beers = new LinkedList<>(Arrays.asList(beerIds));
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Address getAddress() {
    return address;
  }

  public List<String> getBeers() {
    return beers;
  }
}
