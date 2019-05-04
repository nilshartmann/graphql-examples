package nh.graphql.beeradvisor;

import graphql.schema.GraphQLSchema;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.withJsonPath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:beeradvisor", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BeerAdvisorServerTests {

    @LocalServerPort
    int port;

    @Autowired
    GraphQLSchema graphQLSchema;

    @Test
    public void abc() {
        assertThat(graphQLSchema).isNotNull();
    }

    @Before
    public void setupRestAssured() {
        RestAssured.port = port;
    }

    @After
    public void resetRestAssurded() {
        RestAssured.reset();
    }

    @Test
    public void ping() throws Exception  {

        given()
            .contentType("application/json")
            .body("{ \"query\": \"{ ping }\" }")
            .when().post("/graphql").
            then().statusCode(200).body(isJson(withJsonPath("$.data.ping", equalTo("HELLO"))));
    }

   private String query(String q) {
        return String.format("{ \"query\": \"%s\" }", q);
   }


}
