package nh.graphql.plain;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class BeerSchema {
  public static GraphQLSchema create() {
    final GraphQLSchema graphQLSchema = SchemaParser.newParser()
        .file("beer.graphqls")
        .resolvers(new RootResolver()).build()
        .makeExecutableSchema();

    return graphQLSchema;

  }
}
