package nh.backend.beer.graphql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Getter
@AllArgsConstructor
public class ProcessInfo {

  private String name;
  private String uptime;
  private String javaVersion;
  private String graphiQL;
}
