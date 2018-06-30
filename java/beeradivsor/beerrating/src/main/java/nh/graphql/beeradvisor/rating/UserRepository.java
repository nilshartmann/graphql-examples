package nh.graphql.beeradvisor.rating;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class UserRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public User newUser(String id, String name) {
    User user = new User(id, name);
    em.persist(user);

    return user;
  }

}
