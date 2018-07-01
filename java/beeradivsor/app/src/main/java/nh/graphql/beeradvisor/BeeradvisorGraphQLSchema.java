package nh.graphql.beeradvisor;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import nh.graphql.beeradvisor.rating.graphql.RatingRootResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopBeerResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopRootResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeeradvisorGraphQLSchema {

  @Autowired
  private RatingRootResolver ratingRootResolver;

  @Autowired
  private ShopRootResolver shopRootResolver;

  @Autowired
  private ShopBeerResolver shopBeerResolver;

  @Autowired
  private ShopResolver shopResolver;

  @Bean
  public GraphQLSchema graphQLSchema() {
    final GraphQLSchema graphQLSchema = SchemaParser.newParser()
        .file("rating.graphqls")
        .file("shop.graphqls")
        .resolvers(this.ratingRootResolver, this.shopRootResolver, shopResolver, this.shopBeerResolver).build()
        .makeExecutableSchema();

    return graphQLSchema;

  }
}
