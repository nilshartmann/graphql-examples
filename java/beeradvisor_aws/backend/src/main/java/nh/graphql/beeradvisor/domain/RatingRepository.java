package nh.graphql.beeradvisor.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * RatingRepository
 */
@Repository
public class RatingRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public void addRating(Rating rating) {
    em.persist(rating);
  }
}