package nh.graphql.beeradvisor.graphql;

import graphql.schema.GraphQLSchema;
import graphql.servlet.*;
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
 * This configuration requires graphql-java-servlet
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class GraphQLEndpointConfiguration {

    // --- SERVLET --------------------------------------------------------------------------------------------------
    @Bean
    ServletRegistrationBean<GraphQLHttpServlet> graphQLServletRegistrationBean(GraphQLSchema schema) {
        return new ServletRegistrationBean<>(GraphQLHttpServlet.with(schema), "/graphql");
    }

    // --- WEB SOCKET (for Subscriptions) ---------------------------------------------------------------------------
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
