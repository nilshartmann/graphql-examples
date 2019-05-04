package nh.graphql.beeradvisor;

import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static nh.graphql.beeradvisor.Utils.listOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:beeradvisor", "beeradivsor.shop.apiUrl=NOT_CONFIGURED"}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BeerAdvisorTests {


    @Autowired
    GraphQLSchema graphQLSchema;

//    @MockBean
//    ShopService shopService;

    @Test
    public void ping() {
        Map<String, Object> result = query("{ping}");

        assertThat(result).hasSize(1);
    }

    @Test
    public void shops() {
        Map<String, Object> result = query("{\n" +
            "  shops {\n" +
            "    id\n" +
            "  }\n" +
            "}");

        System.out.println("result" + result);

        assertThat(result).hasSize(1);
        assertThat(result).containsKey("data");
    }

    @Test
    public void beersShops() {
        Map<String, Object> result = query("{\n" +
            "  beers {\n" +
            "    shops {\n" +
            "      id beers { shops { id } }\n" +
            "    }\n" +
            "  }\n" +
            "}\n");

//        Mockito.verify(shopService).findShopsForBeer(listOf("B1"));
//        Mockito.verify(shopService).findShopsForBeer(listOf("B2"));
//        Mockito.verify(shopService).findShopsForBeer(listOf("B3"));
//        Mockito.verify(shopService).findShopsForBeer(listOf("B4"));
//        Mockito.verify(shopService).findShopsForBeer(listOf("B5"));
//        Mockito.verify(shopService).findShopsForBeer(listOf("B6"));
    }

    private Map<String, Object> query(String q) {
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        ExecutionInput executionInput =
            ExecutionInput.newExecutionInput()
                .query
                    ("query " + q).build();

        Map<String, Object> data =
            graphQL.execute(executionInput).toSpecification();

        return data;
    }


}
