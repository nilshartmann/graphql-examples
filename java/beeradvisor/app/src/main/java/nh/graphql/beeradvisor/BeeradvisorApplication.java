package nh.graphql.beeradvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@SpringBootApplication
@Configuration
public class BeeradvisorApplication {

  /**
   * Keeps the session open until the end of a request. Allows us to use lazy-loading with Hibernate.
   */
  @Bean
  public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
    return new OpenEntityManagerInViewFilter();
  }

	public static void main(String[] args) {
		SpringApplication.run(BeeradvisorApplication.class, args);
	}
}
