package nh.backend.beer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
  private String id;
  private String name;
  private String price;
}
