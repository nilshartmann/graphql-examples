package nh.graphql.beeradvisor;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import nh.graphql.beeradvisor.rating.graphql.RatingRootResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopBeerResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopRootResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JUST AN EXAMPLE: HOW TO CONFIGURE GRAPHQL (SCHEMA AND SERVLET) "MANUALLY" WITHOUT SPRING BOOT STARTER
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class ExampleGraphQLConfiguration {
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
  @Bean
  ServletRegistrationBean<SimpleGraphQLServlet> graphQLServletRegistrationBean(GraphQLSchema schema) {
    final DefaultGraphQLSchemaProvider schemaProvider = new DefaultGraphQLSchemaProvider(schema);
    SimpleGraphQLServlet.Builder builder = SimpleGraphQLServlet.builder(schemaProvider)
        .withExecutionStrategyProvider(new DefaultExecutionStrategyProvider());

    return new ServletRegistrationBean<>(builder.build(), "/graphql");
  }

}
