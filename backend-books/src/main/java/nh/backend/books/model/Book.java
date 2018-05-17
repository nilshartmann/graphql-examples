package nh.backend.books.model;

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
public class Book {
  private String id;
  private String title;
  private String price;
}
