package nh.graphql.beerrating;

import nh.graphql.beerrating.model.Beer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class BeerRepository {

  @PersistenceContext
  private EntityManager em;

  public List<Beer> findAll() {
    final TypedQuery<Beer> query = em.createQuery("SELECT b FROM Beer b", Beer.class);
    return query.getResultList();
  }

  public Beer getBeer(String beerId) {
    return em.find(Beer.class, beerId);
  }

  @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addBeer(Beer beer) {
    em.persist(beer);
  }

}
