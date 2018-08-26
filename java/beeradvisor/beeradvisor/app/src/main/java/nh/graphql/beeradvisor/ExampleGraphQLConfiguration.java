package nh.graphql.beeradvisor;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.SubscriptionExecutionStrategy;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import nh.graphql.beeradvisor.rating.graphql.RatingMutationResolver;
import nh.graphql.beeradvisor.rating.graphql.RatingQueryResolver;
import nh.graphql.beeradvisor.rating.graphql.RatingSubscriptionResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopBeerResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopResolver;
import nh.graphql.beeradvisor.shop.graphql.ShopRootResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

/**
 * JUST AN EXAMPLE: HOW TO CONFIGURE GRAPHQL (SCHEMA AND SERVLET) "MANUALLY"
 * WITHOUT SPRING BOOT STARTER
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class ExampleGraphQLConfiguration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private RatingQueryResolver ratingQueryResolver;

  @Autowired
  private RatingMutationResolver ratingMutationResolver;

  @Autowired
  private RatingSubscriptionResolver ratingSubscriptionResolver;

  @Autowired
  private ShopRootResolver shopRootResolver;

  @Autowired
  private ShopBeerResolver shopBeerResolver;

  @Autowired
  private ShopResolver shopResolver;

  @Bean
  public GraphQLSchema graphQLSchema() {
    final GraphQLSchema graphQLSchema = SchemaParser.newParser().file("rating.graphqls").file("shop.graphqls")
        .resolvers(this.ratingQueryResolver, this.ratingMutationResolver, this.ratingSubscriptionResolver,
            this.shopRootResolver, shopResolver, this.shopBeerResolver)
        .build().makeExecutableSchema();

    return graphQLSchema;

  }

  @Bean
  ServletRegistrationBean<SimpleGraphQLHttpServlet> graphQLServletRegistrationBean(GraphQLSchema schema) {
    SimpleGraphQLHttpServlet.Builder builder = SimpleGraphQLHttpServlet.newBuilder(schema);
    final SimpleGraphQLHttpServlet servlet = builder.build();
    return new ServletRegistrationBean<>(servlet, "/graphql");
  }

  @Bean
  public ServerEndpointRegistration serverEndpointRegistration(GraphQLSchema schema) {
    final DefaultGraphQLSchemaProvider schemaProvider = new DefaultGraphQLSchemaProvider(schema);
    final DefaultExecutionStrategyProvider executionStrategyProvider = new DefaultExecutionStrategyProvider(
        new AsyncExecutionStrategy(), new AsyncExecutionStrategy(), new SubscriptionExecutionStrategy());
    final GraphQLQueryInvoker queryInvoker = GraphQLQueryInvoker.newBuilder().withExecutionStrategyProvider(executionStrategyProvider).build();

    final GraphQLWebsocketServlet websocketServlet = new GraphQLWebsocketServlet(queryInvoker, GraphQLInvocationInputFactory.newBuilder(schemaProvider).build(), GraphQLObjectMapper.newBuilder().build());
    return new GraphQLWsServerEndpointRegistration("/subscriptions", websocketServlet);
  }

  @Bean
  @ConditionalOnMissingBean
  public ServerEndpointExporter serverEndpointExporter() {
    logger.info("Register ServerEndpointExporter !!!");
    return new ServerEndpointExporter();
  }

}
