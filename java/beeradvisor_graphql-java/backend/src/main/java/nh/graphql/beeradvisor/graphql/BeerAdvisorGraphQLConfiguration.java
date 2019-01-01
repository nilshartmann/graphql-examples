package nh.graphql.beeradvisor.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import nh.graphql.beeradvisor.auth.graphql.LoginResolver;
import nh.graphql.beeradvisor.graphql.mutation.BeerAdvisorMutationResolver;
import nh.graphql.beeradvisor.graphql.query.BeerAdvisorQueryResolver;
import nh.graphql.beeradvisor.graphql.query.BeerFieldResolver;
import nh.graphql.beeradvisor.graphql.query.ShopFieldResolver;
import nh.graphql.beeradvisor.graphql.subscription.BeerAdvisorSubscriptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * JUST AN EXAMPLE: HOW TO CONFIGURE GRAPHQL (SCHEMA AND SERVLET) "MANUALLY"
 * WITHOUT SPRING BOOT STARTER
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeerAdvisorGraphQLConfiguration {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final BeerAdvisorQueryResolver beerAdvisorQueryResolver;
  private final BeerAdvisorMutationResolver beerAdvisorMutationResolver;
  private final BeerFieldResolver beerFieldResolver;
  private final BeerAdvisorSubscriptionResolver beerAdvisorSubscriptionResolver;
  private final ShopFieldResolver shopFieldResolver;
  private final LoginResolver loginResolver;

  @Autowired
  public BeerAdvisorGraphQLConfiguration(BeerAdvisorQueryResolver beerAdvisorQueryResolver, BeerAdvisorMutationResolver beerAdvisorMutationResolver, BeerFieldResolver beerFieldResolver, BeerAdvisorSubscriptionResolver beerAdvisorSubscriptionResolver, ShopFieldResolver shopFieldResolver, LoginResolver loginResolver) {
    this.beerAdvisorQueryResolver = beerAdvisorQueryResolver;
    this.beerAdvisorMutationResolver = beerAdvisorMutationResolver;
    this.beerFieldResolver = beerFieldResolver;
    this.beerAdvisorSubscriptionResolver = beerAdvisorSubscriptionResolver;
    this.shopFieldResolver = shopFieldResolver;
    this.loginResolver = loginResolver;
  }

  @Bean
  public GraphQLSchema graphQLSchema() {
    final GraphQLSchema graphQLSchema = SchemaParser.newParser() //
        .files("schema/rating.graphqls", "schema/shop.graphqls", "schema/auth.graphqls")
        .resolvers(this.beerAdvisorQueryResolver, this.beerAdvisorMutationResolver, this.beerAdvisorSubscriptionResolver,
            this.beerFieldResolver, shopFieldResolver,
            this.loginResolver) // authentication/login
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
    final GraphQLQueryInvoker queryInvoker = GraphQLQueryInvoker.newBuilder().build();

    final GraphQLWebsocketServlet websocketServlet = new GraphQLWebsocketServlet(queryInvoker, GraphQLInvocationInputFactory.newBuilder(schemaProvider).build(), GraphQLObjectMapper.newBuilder().build());
    return new GraphQLWsServerEndpointRegistration("/subscriptions", websocketServlet);
  }

  @Bean
  @ConditionalOnMissingBean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

  class GraphQLWsServerEndpointRegistration extends ServerEndpointRegistration implements Lifecycle {

    private final GraphQLWebsocketServlet servlet;

    public GraphQLWsServerEndpointRegistration(String path, GraphQLWebsocketServlet servlet) {
      super(path, servlet);
      this.servlet = servlet;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
      super.modifyHandshake(sec, request, response);
      servlet.modifyHandshake(sec, request, response);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
      servlet.beginShutDown();
    }

    @Override
    public boolean isRunning() {
      return !servlet.isShutDown();
    }
  }

}
