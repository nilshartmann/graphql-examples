package nh.graphql.plain;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class StandaloneExample {

  public static void main(String[] args) {

    // Create 'executable' Schema
    GraphQLSchema beerSchema = BeerSchema.create();

    // Create GraphQL Instance
    GraphQL graphQL = GraphQL.newGraphQL(beerSchema).build();

    // Build Query
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { beers { name ratings { author } } }")
        .build();

    // Run Query
    ExecutionResult executionResult = graphQL.execute(executionInput);

    final Object data = executionResult.getData();

    System.out.println(data);

  }

}
