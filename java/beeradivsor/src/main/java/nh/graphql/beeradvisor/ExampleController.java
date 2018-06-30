package nh.graphql.beeradvisor;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nh.graphql.beeradvisor.rating.BeerRepository;
import nh.graphql.beeradvisor.rating.Beer;
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
public class ExampleController {

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
    result.put("user", b.getRatings().get(0).getUser().getName());

    return result;
  }

  /**
   * "Complete" Query => requests all three domain objects
   *
   * @return
   */
  @GetMapping("/gql/all")
  @ResponseBody
  public Object gql() {
    // Create GraphQL Instance
    GraphQL graphQL = GraphQL.newGraphQL(schema).build();

    // Build Query
    // Hibernate: select beer0_.id as id1_2_0_, rating2_.id as id1_4_1_, author3_.id as id1_0_2_, beer0_.name as name2_2_0_, beer0_.price as price3_2_0_, rating2_.author_id as author_i4_4_1_, rating2_.beer_id as beer_id5_4_1_, rating2_.comment as comment2_4_1_, rating2_.stars as stars3_4_1_, ratings1_.beer_id as beer_id1_3_0__, ratings1_.ratings_id as ratings_2_3_0__, author3_.name as name2_0_2_ from beer beer0_ left outer join beer_ratings ratings1_ on beer0_.id=ratings1_.beer_id left outer join rating rating2_ on ratings1_.ratings_id=rating2_.id left outer join author author3_ on rating2_.author_id=author3_.id
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { beers { id name ratings { stars user { name } } } }")
        .build();

    // Run Query
    ExecutionResult executionResult = graphQL.execute(executionInput);

    final Object data = executionResult.getData();

    return data;
  }

  @GetMapping("/gql/shops")
  @ResponseBody
  public Object gqlShops() {
    // Create GraphQL Instance
    GraphQL graphQL = GraphQL.newGraphQL(schema).build();

    // Build Query
    // Hibernate: select beer0_.id as id1_2_0_, rating2_.id as id1_4_1_, author3_.id as id1_0_2_, beer0_.name as name2_2_0_, beer0_.price as price3_2_0_, rating2_.author_id as author_i4_4_1_, rating2_.beer_id as beer_id5_4_1_, rating2_.comment as comment2_4_1_, rating2_.stars as stars3_4_1_, ratings1_.beer_id as beer_id1_3_0__, ratings1_.ratings_id as ratings_2_3_0__, author3_.name as name2_0_2_ from beer beer0_ left outer join beer_ratings ratings1_ on beer0_.id=ratings1_.beer_id left outer join rating rating2_ on ratings1_.ratings_id=rating2_.id left outer join author author3_ on rating2_.author_id=author3_.id
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { beers  {  name shops { name } } }")
        .build();

    // Run Query
    ExecutionResult executionResult = graphQL.execute(executionInput);

    final Object data = executionResult.getData();

    return data;
  }

  /**
   * "partial" query 1 => requests one beer + rating, but no author
   *
   * @return
   */
  @GetMapping("/gql/ratings")
  @ResponseBody
  public Object gqlratings() {
    // Create GraphQL Instance
    GraphQL graphQL = GraphQL.newGraphQL(schema).build();

    // Build Query
    // Hibernate: select beer0_.id as id1_2_0_, rating2_.id as id1_4_1_, beer0_.name as name2_2_0_, beer0_.price as price3_2_0_, rating2_.author_id as author_i4_4_1_, rating2_.beer_id as beer_id5_4_1_, rating2_.comment as comment2_4_1_, rating2_.stars as stars3_4_1_, ratings1_.beer_id as beer_id1_3_0__, ratings1_.ratings_id as ratings_2_3_0__ from beer beer0_ left outer join beer_ratings ratings1_ on beer0_.id=ratings1_.beer_id left outer join rating rating2_ on ratings1_.ratings_id=rating2_.id
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { beers { id name ratings { stars } } }")
        .build();

    // Run Query
    ExecutionResult executionResult = graphQL.execute(executionInput);

    final Object data = executionResult.getData();

    return data;
  }

  /**
   * "partial" query 1 => requests one beer + rating, but no author
   *
   * @return
   */
  @GetMapping("/gql/beers")
  @ResponseBody
  public Object gqlbeers() {
    // Create GraphQL Instance
    GraphQL graphQL = GraphQL.newGraphQL(schema).build();

    // Build Query
    // Hibernate: select beer0_.id as id1_2_, beer0_.name as name2_2_, beer0_.price as price3_2_ from beer beer0_
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { beers { id price } }")
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
