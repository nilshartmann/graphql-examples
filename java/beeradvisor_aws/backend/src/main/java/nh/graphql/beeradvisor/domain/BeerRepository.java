package nh.graphql.beeradvisor.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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

    final TypedQuery<Beer> query = em.createQuery("SELECT b FROM Beer b", Beer.class);
    if (entityGraph != null) {
      query.setHint("javax.persistence.fetchgraph", entityGraph);
    }

    return query.getResultList();


  }
}
