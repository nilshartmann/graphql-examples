package nh.backend.beer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nh.backend.beer.model.BeerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeerApplicationTests {

  @Autowired
  private BeerRepository beerRepository;

  @Test
  public void contextLoads() {
    assertThat(this.beerRepository, is(notNullValue()));
  }

}
