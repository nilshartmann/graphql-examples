package nh.graphql.beeradvisor.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ShopRepository {
  private static Logger logger = LoggerFactory.getLogger(ShopRepository.class);

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void addShop(Shop shop) {
    entityManager.persist(shop);
  }

  @Transactional
  public List<Shop> findAll() {
    TypedQuery<Shop> query = entityManager.createQuery("select s FROM Shop s ORDER BY s.id", Shop.class);
    return query.getResultList();
  }

  @Transactional
  public List<Shop> findShopsWithBeer(String beerId) {
    logger.info("Finding Shops selling beer with id '{}'", beerId);
    TypedQuery<Shop> query = entityManager.createQuery("select s FROM Shop s WHERE :beer MEMBER OF s.beers", Shop.class);
    query.setParameter("beer", beerId);
    return query.getResultList();
  }

  @Transactional
  public Shop findShop(String shopId) {
    logger.info("Find shop with id '{}'", shopId);
    return entityManager.find(Shop.class, shopId);
  }
}
