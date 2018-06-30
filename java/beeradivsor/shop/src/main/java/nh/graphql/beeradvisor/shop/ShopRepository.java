package nh.graphql.beeradvisor.shop;

import nh.graphql.beeradvisor.shop.Shop;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class ShopRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addShop(Shop shop) {
    em.persist(shop);
  }

}
