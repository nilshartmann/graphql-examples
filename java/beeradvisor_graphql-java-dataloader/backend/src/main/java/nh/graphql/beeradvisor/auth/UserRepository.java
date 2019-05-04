package nh.graphql.beeradvisor.auth;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class UserRepository {
//
//  @PersistenceContext
//  private EntityManager em;
//
//  @Transactional
//  public User newUser(String id, String login, String name) {
//    User user = new User(id, login.toLowerCase(), name);
//    em.persist(user);
//
//    return user;
//  }
//
//  @Transactional
//  public User getUser(String userId) {
//    return em.find(User.class, userId);
//  }
//
//  @Transactional
//  public User getUserByLogin(String login) {
//    final TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u WHERE u.login=:login", User.class);
//    query.setParameter("login", login.toLowerCase());
//    List<User> result = query.getResultList();
//    if (result.isEmpty()) {
//      return null;
//    }
//    return result.get(0);
//  }

}
