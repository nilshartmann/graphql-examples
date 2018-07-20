package nh.graphql.beeradvisor.rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * RatingRepository
 *
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