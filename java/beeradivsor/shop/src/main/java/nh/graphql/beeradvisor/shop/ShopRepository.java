package nh.graphql.beeradvisor.shop;

import nh.graphql.beeradvisor.rating.Beer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ShopRepository {

  private static Logger logger = LoggerFactory.getLogger(ShopRepository.class);

  @PersistenceContext
  private EntityManager em;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addShop(Shop shop) {
    em.persist(shop);
  }

  public List<Shop> findAll() {
    final TypedQuery<Shop> query = em.createQuery("SELECT s FROM Shop s", Shop.class);
    return query.getResultList();
  }

  public List<Shop> sellsBeer(String beerId) {
    final TypedQuery<Shop> query = em.createQuery("SELECT s FROM Shop s JOIN s.beers b WHERE b = :beerId", Shop.class);
    query.setParameter("beerId", beerId);
    return query.getResultList();
  }

  public Shop findById(String shopId) {
    return em.find(Shop.class, shopId);
  }
}
