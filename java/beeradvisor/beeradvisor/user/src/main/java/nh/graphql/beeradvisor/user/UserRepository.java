package nh.graphql.beeradvisor.user;

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

  @Transactional
  public User newUser(String id, String name) {
    User user = new User(id, name);
    em.persist(user);

    return user;
  }

  @Transactional
  public User getUser(String userId) {
    return em.find(User.class, userId);
  }

}
