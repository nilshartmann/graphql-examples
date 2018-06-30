package nh.graphql.beeradvisor;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import nh.graphql.beeradvisor.rating.graphql.RootResolver;
import nh.graphql.beeradvisor.shop.graphql.BeerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeeradvisorGraphQLSchema {

  @Autowired
  private RootResolver rootResolver;

  @Autowired
  private BeerResolver beerResolver;

  @Bean
  public GraphQLSchema graphQLSchema() {
    final GraphQLSchema graphQLSchema = SchemaParser.newParser()
        .file("beer.graphqls")
        .file("shop.graphqls")
        .resolvers(this.rootResolver, this.beerResolver).build()
        .makeExecutableSchema();

    return graphQLSchema;

  }
}
