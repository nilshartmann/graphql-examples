package nh.graphql.beerrating;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nh.graphql.beerrating.model.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class BeerController {

  @Autowired
  private BeerRepository beerRepository;

  @Autowired
  private GraphQLSchema schema;

  /**
   * EXAMPLE: runs n queries in database (beer + ratings)
   *
   * @return
   */
  @GetMapping("/beers")
  @ResponseBody
  public Map<String, Object> beers() {
    Beer b = beerRepository.findAll().get(0);
    Map<String, Object> result = new Hashtable<>();
    result.put("name", b.getName());
    result.put("ratings", b.getRatings().size());

    return result;
  }

  /**
   * EXAMPLE: runs only a single query due to the entitygraph in the repository
   *
   * @return
   */
  @GetMapping("/fetchbeers")
  @ResponseBody
  public Map<String, Object> fetchbeers() {
    Beer b = beerRepository.findAllFetchGraph(false).get(0);
    Map<String, Object> result = new Hashtable<>();
    result.put("name", b.getName());
    result.put("ratings", b.getRatings().size());

    return result;
  }

  /**
   * EXAMPLE: runs only a single query due to the entitygraph in the repository,
   * even the referenced author is configured as lazy-loading in mapping
   *
   * @return
   */
  @GetMapping("/fetchall")
  @ResponseBody
  public Map<String, Object> fetchall() {
    Beer b = beerRepository.findAllFetchGraph(true).get(0);
    Map<String, Object> result = new Hashtable<>();
    result.put("name", b.getName());
    result.put("ratings", b.getRatings().size());
    result.put("author", b.getRatings().get(0).getAuthor().getName());

    return result;
  }

  @GetMapping("/gql")
  @ResponseBody
  public Object gql() {
    // Create GraphQL Instance
    GraphQL graphQL = GraphQL.newGraphQL(schema).build();

    // Build Query
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { beers { name ratings { author { name } } } }")
        .build();

    // Run Query
    ExecutionResult executionResult = graphQL.execute(executionInput);

    final Object data = executionResult.getData();

    return data;
  }

  @GetMapping("/beer")
  @ResponseBody
  public Map<String, Object> beer() {
    Beer b = beerRepository.getBeer("B1");
    Map result = new Hashtable<String, String>();
    result.put("name", b.getName());
    result.put("ratings", b.getRatings().size());

    return result;
  }

}
