package nh.graphql.beeradvisor.graphql;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.servlet.GraphQLHttpServlet;
import nh.graphql.beeradvisor.auth.graphql.LoginDataFetchers;
import nh.graphql.beeradvisor.graphql.fetchers.BeerAdvisorDataFetcher;
import nh.graphql.beeradvisor.graphql.fetchers.BeerDataFetchers;
import nh.graphql.beeradvisor.graphql.fetchers.ShopFieldDataFetchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeerAdvisorGraphQLConfiguration {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BeerAdvisorDataFetcher beerAdvisorDataFetcher;
    @Autowired
    private BeerDataFetchers beerDataFetchers;
    @Autowired
    private ShopFieldDataFetchers shopFieldDataFetchers;
    @Autowired
    private LoginDataFetchers loginDataFetchers;


    @Value("classpath:/schema/beeradvisor.graphqls")
    private Resource beerAdvisorSchemaResource;

    @Bean
    public GraphQLSchema graphQLSchema() throws Exception {

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(new InputStreamReader(beerAdvisorSchemaResource.getInputStream()));
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

//        SchemaParser p = new SchemaParser();
//        final TypeDefinitionRegistry parse = p.parse("");
//        TypeDefinitionRegistry r;
//        r.add(p);
//        final GraphQLSchema graphQLSchema = SchemaParser.newParser() //
//            .files("schema/rating.graphqls", "schema/shop.graphqls", "schema/auth.graphqls")
//            .resolvers(this.beerAdvisorDataFetcher, this.beerAdvisorMutationResolver, this.beerAdvisorSubscriptionResolver,
//                this.beerDataFetchers, shopFieldDataFetchers,
//                this.loginDataFetchers) // authentication/login
//            .build().makeExecutableSchema();

    private RuntimeWiring buildWiring() {
        final RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type(newTypeWiring("Query")
                .dataFetcher("beer", beerAdvisorDataFetcher.beerFetcher())
                .dataFetcher("beers", beerAdvisorDataFetcher.beersFetcher())
                .dataFetcher("shops", beerAdvisorDataFetcher.shopsFetcher())
                .dataFetcher("shop", beerAdvisorDataFetcher.shopFetcher()))
            .type(newTypeWiring("Beer")
                .dataFetcher("averageStars", beerDataFetchers.averageStarsFetcher())
                .dataFetcher("ratingsWithStars", beerDataFetchers.ratingsWithStarsFetcher())
                .dataFetcher("shops", beerDataFetchers.shopsFetcher()))
            .type(newTypeWiring("Shop")
                .dataFetcher("address", shopFieldDataFetchers.addressFetcher())
                .dataFetcher("beers", shopFieldDataFetchers.beersFetcher()))
            .type(newTypeWiring("Mutation")
                .dataFetcher("login", loginDataFetchers.getLoginDataFetcher())
                .dataFetcher("addRating", beerAdvisorDataFetcher.addRatingFetcher())
            )
//            .type(newTypeWiring("Subscription")
//                .dataFetcher("onDataChangeEvent", subscriptionWiring.dataChangeEventFetcher))
//            .type(newTypeWiring("DataChangePayload")
//                .dataFetcher("after", subscriptionWiring.afterFieldsFetcher))
            .build();

        return runtimeWiring;
    }

  @Bean
  public ServletRegistrationBean graphQLServletRegistrationBean(GraphQLSchema schema) {
      final GraphQLHttpServlet servlet = GraphQLHttpServlet.with(schema);
      return new ServletRegistrationBean<GraphQLHttpServlet>(servlet, "/graphql");
  }
//
//  @Bean
//  public ServerEndpointRegistration serverEndpointRegistration(GraphQLSchema schema) {
//    final DefaultGraphQLSchemaProvider schemaProvider = new DefaultGraphQLSchemaProvider(schema);
//    final GraphQLQueryInvoker queryInvoker = GraphQLQueryInvoker.newBuilder().build();
//
//    final GraphQLWebsocketServlet websocketServlet = new GraphQLWebsocketServlet(queryInvoker, GraphQLInvocationInputFactory.newBuilder(schemaProvider).build(), GraphQLObjectMapper.newBuilder().build());
//    return new GraphQLWsServerEndpointRegistration("/subscriptions", websocketServlet);
//  }
//
//  @Bean
//  @ConditionalOnMissingBean
//  public ServerEndpointExporter serverEndpointExporter() {
//    return new ServerEndpointExporter();
//  }
//
//  class GraphQLWsServerEndpointRegistration extends ServerEndpointRegistration implements Lifecycle {
//
//    private final GraphQLWebsocketServlet servlet;
//
//    public GraphQLWsServerEndpointRegistration(String path, GraphQLWebsocketServlet servlet) {
//      super(path, servlet);
//      this.servlet = servlet;
//    }
//
//    @Override
//    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//      super.modifyHandshake(sec, request, response);
//      servlet.modifyHandshake(sec, request, response);
//    }
//
//    @Override
//    public void start() {
//    }
//
//    @Override
//    public void stop() {
//      servlet.beginShutDown();
//    }
//
//    @Override
//    public boolean isRunning() {
//      return !servlet.isShutDown();
//    }
//  }

}
