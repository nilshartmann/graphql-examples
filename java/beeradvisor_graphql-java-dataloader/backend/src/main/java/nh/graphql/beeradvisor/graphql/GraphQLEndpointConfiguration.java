package nh.graphql.beeradvisor.graphql;

import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * This configuration requires graphql-java-servlet
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@ConditionalOnWebApplication
@Configuration
public class GraphQLEndpointConfiguration {

    class BeerAdvisorContextBuilder extends DefaultGraphQLContextBuilder {

        private final BeerAdvisorDataLoaderConfigurer dataLoaderConfigurer;

        private BeerAdvisorContextBuilder(BeerAdvisorDataLoaderConfigurer dataLoaderConfigurer) {
            this.dataLoaderConfigurer = dataLoaderConfigurer;
        }

        private GraphQLContext setupDataLoaderRegistry(final GraphQLContext context) {
            final DataLoaderRegistry dataLoaderRegistry = dataLoaderConfigurer.configureDataLoader(context.getDataLoaderRegistry());
            context.setDataLoaderRegistry(dataLoaderRegistry);
            return context;
        }


        @Override
        public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
            GraphQLContext context = super.build(httpServletRequest, httpServletResponse);
            return setupDataLoaderRegistry(context);
        }

        @Override
        public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
            GraphQLContext context = super.build(session, handshakeRequest);
            return setupDataLoaderRegistry(context);
        }

        @Override
        public GraphQLContext build() {
            GraphQLContext context = super.build();
            return setupDataLoaderRegistry(context);
        }
    }

    // --- SERVLET --------------------------------------------------------------------------------------------------
    @Bean
    ServletRegistrationBean<GraphQLHttpServlet> graphQLServletRegistrationBean(GraphQLSchema schema, BeerAdvisorDataLoaderConfigurer dataLoaderConfigurer) {

        GraphQLConfiguration config = GraphQLConfiguration.with(schema).with(new BeerAdvisorContextBuilder(dataLoaderConfigurer)).build();

        return new ServletRegistrationBean<>(GraphQLHttpServlet.with(config), "/graphql");

//        return new ServletRegistrationBean<>(GraphQLHttpServlet.with(
//            GraphQLConfiguration.with(
//
//            ).build()
//
//        )with(schema)
//            .
//            , "/graphql");
    }

    // --- WEB SOCKET (for Subscriptions) ---------------------------------------------------------------------------
    @Bean
    public ServerEndpointRegistration serverEndpointRegistration(GraphQLSchema schema, BeerAdvisorDataLoaderConfigurer dataLoaderConfigurer) {
        final DefaultGraphQLSchemaProvider schemaProvider = new DefaultGraphQLSchemaProvider(schema);
        final GraphQLQueryInvoker queryInvoker = GraphQLQueryInvoker.newBuilder(). build();

        final GraphQLInvocationInputFactory graphQLInvocationInputFactory = GraphQLInvocationInputFactory.newBuilder(schemaProvider).withGraphQLContextBuilder(new BeerAdvisorContextBuilder(dataLoaderConfigurer)).build();


        final GraphQLWebsocketServlet websocketServlet = new GraphQLWebsocketServlet(queryInvoker, graphQLInvocationInputFactory, GraphQLObjectMapper.newBuilder().build());
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
