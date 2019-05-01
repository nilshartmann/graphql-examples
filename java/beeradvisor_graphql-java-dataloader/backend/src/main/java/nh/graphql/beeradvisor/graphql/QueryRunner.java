package nh.graphql.beeradvisor.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RestController
public class QueryRunner {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GraphQLSchema schema;

	@Transactional
	@GetMapping("/q")
	public void runQuery() {
		GraphQL graphQL = GraphQL.newGraphQL(schema).build();

		ExecutionInput executionInput =
			ExecutionInput.newExecutionInput()
				.query
					("query { beers { id name ratings { stars } } }").build();

        Map<String, Object> data =
			graphQL.execute(executionInput).toSpecification();

		logger.info("\n\n\n\n{}\n\n\n\n", data);

		System.out.println(data);

	}
}
