package nh.graphql.beerrating;

import nh.graphql.beerrating.model.Author;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class AuthorRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Author newAuthor(String id, String name) {
    Author author = new Author(id, name);
    em.persist(author);

    return author;
  }

}
