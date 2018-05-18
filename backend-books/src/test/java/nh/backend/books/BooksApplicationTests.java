package nh.backend.books;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nh.backend.books.model.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksApplicationTests {

  @Autowired
  private BookRepository bookRepository;

  @Test
  public void contextLoads() {
    assertThat(this.bookRepository, is(notNullValue()));
  }

}
