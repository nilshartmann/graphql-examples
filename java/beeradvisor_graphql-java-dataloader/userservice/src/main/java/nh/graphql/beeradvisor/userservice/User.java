package nh.graphql.beeradvisor.userservice;

import lombok.Data;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Data
public class User {
    private final String id;
    private final String login;
    private final String name;
}
