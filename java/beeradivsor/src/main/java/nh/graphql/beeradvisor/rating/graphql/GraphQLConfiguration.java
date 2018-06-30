package nh.graphql.beeradvisor.rating.graphql;

import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class GraphQLConfiguration {

  @Bean
  ServletRegistrationBean<SimpleGraphQLServlet> graphQLServletRegistrationBean(GraphQLSchema schema) {
    final DefaultGraphQLSchemaProvider schemaProvider = new DefaultGraphQLSchemaProvider(schema);
    SimpleGraphQLServlet.Builder builder = SimpleGraphQLServlet.builder(schemaProvider)
        .withExecutionStrategyProvider(new DefaultExecutionStrategyProvider());

    return new ServletRegistrationBean<>(builder.build(), "/graphql");
  }

}
