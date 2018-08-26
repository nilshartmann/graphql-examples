package nh.graphql.plain;

import graphql.servlet.SimpleGraphQLServlet;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;
import io.undertow.servlet.util.ImmediateInstanceHandle;

import javax.servlet.Servlet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ServletExample {

  public static void main(String[] args) throws Exception {

    // example query:
    // http localhost:8080/graphql?query={beers{name ratings { author comment }}


//    System.out.println(executionResult);

    DeploymentInfo servletBuilder = Servlets.deployment()
        .setDeploymentName("GRAPHQL")
        .setClassLoader(ServletExample.class.getClassLoader())
        .setContextPath("/")
        .addServlets(
            Servlets.servlet("GraphQL", SimpleGraphQLServlet.class,
                new InstanceFactory<Servlet>() {
                  @Override
                  public InstanceHandle<Servlet> createInstance() throws InstantiationException {
                    final SimpleGraphQLServlet servlet = SimpleGraphQLServlet.builder(BeerSchema.create()).build();
                    return new ImmediateInstanceHandle(servlet);
                  }
                }
            ).addMapping("/graphql"));

    DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
    manager.deploy();
    HttpHandler httpHandler = manager.start();
    // PathHandler path = Handlers.path(Handlers.redirect("/myapp"))
    //     .addPrefixPath("/myapp", manager.start());

    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(httpHandler)
        .build();
    server.start();


  }


}
