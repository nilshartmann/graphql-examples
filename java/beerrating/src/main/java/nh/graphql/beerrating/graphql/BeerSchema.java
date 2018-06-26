package nh.graphql.beerrating.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeerSchema {

  @Autowired
  private RootResolver rootResolver;

  @Bean
  public GraphQLSchema graphQLSchema() {
    final GraphQLSchema graphQLSchema = SchemaParser.newParser()
        .file("beer.graphqls")
        .resolvers(this.rootResolver).build()
        .makeExecutableSchema();

    return graphQLSchema;

  }
}
