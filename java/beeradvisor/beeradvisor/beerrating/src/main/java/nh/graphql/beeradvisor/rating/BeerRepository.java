package nh.graphql.beeradvisor.rating;

import nh.graphql.beeradvisor.rating.graphql.RatingQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class BeerRepository {

  private final Logger logger = LoggerFactory.getLogger(BeerRepository.class);

  @PersistenceContext
  private EntityManager em;

  public List<Beer> findAll() {
    final TypedQuery<Beer> query = em.createQuery("SELECT b FROM Beer b", Beer.class);
    return query.getResultList();
  }

  public List<Beer> findWithIds(List<String> beerIds) {
    final TypedQuery<Beer> query = em.createQuery("SELECT b FROM Beer b WHERE b.id IN :beerIds", Beer.class);
    query.setParameter("beerIds", beerIds);
    return query.getResultList();
  }

  public List<Beer> findAllFetchGraph(boolean withAuthor) {
    EntityGraph entityGraph = em.createEntityGraph(Beer.class);
    entityGraph.addAttributeNodes("ratings");
    if (withAuthor) {
      entityGraph.addSubgraph("ratings").addAttributeNodes("author");
    }

    final TypedQuery<Beer> query = em.createQuery("SELECT b FROM Beer b", Beer.class);
    query.setHint("javax.persistence.fetchgraph", entityGraph);

    return query.getResultList();
  }

  public Beer getBeer(String beerId) {
    return em.find(Beer.class, beerId);
  }

  @Transactional
  public void saveBeer(Beer beer) {
    em.persist(beer);
  }

  public List<Beer> findAllWithEntityGraph(List<String> allRequestedRelations) {



    logger.info("findAllWithEntityGraph requestedRelations:\n{}",
        allRequestedRelations.stream().reduce("", (r, x) -> format("%s - %s%n", r, x)));

    final EntityGraph entityGraph = allRequestedRelations.size() > 0 ? em.createEntityGraph(Beer.class) : null;

    if (allRequestedRelations.size() > 0) {
      // very basic and stupid implementation
      for (String relation : allRequestedRelations) {
        String[] parts = relation.split("/");
        Object base = entityGraph;
        for (int i = 0; i < parts.length; i++) {
          String part = parts[i];
          if (i == parts.length - 1) {
            // last part => add attributeNode
            if (base instanceof EntityGraph) {
              ((EntityGraph) base).addAttributeNodes(parts[i]);
            } else {
              ((Subgraph) base).addAttributeNodes(parts[i]);
            }
          } else {
            if (base instanceof EntityGraph) {
              base = ((EntityGraph) base).addSubgraph(parts[i]);
            } else {
              base = ((Subgraph) base).addSubgraph(parts[i]);
            }
          }
        }
      }
    }

    logger.info("Using fetchgraph {}", entityGraph);

    final TypedQuery<Beer> query = em.createQuery("SELECT b FROM Beer b", Beer.class);
    if (entityGraph != null) {
      query.setHint("javax.persistence.fetchgraph", entityGraph);
    }

    return query.getResultList();


  }
}
