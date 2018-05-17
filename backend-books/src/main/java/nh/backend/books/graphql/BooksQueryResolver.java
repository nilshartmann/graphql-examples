package nh.backend.books.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nh.backend.books.model.Book;
import nh.backend.books.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BooksQueryResolver implements GraphQLQueryResolver {

  private final long bootTime;
  private final BookRepository bookRepository;

  @Autowired
  public BooksQueryResolver(BookRepository bookRepository) {
    this.bookRepository = bookRepository;

    bootTime = System.currentTimeMillis();
  }

  public List<Book> books() {
    return bookRepository.findAll();
  }

  public ProcessInfo ping() {
    return new ProcessInfo("The Books Backend", String.format("Up and running for %d s", (System.currentTimeMillis()-this.bootTime) / 1000), "1.8");
  }

}
