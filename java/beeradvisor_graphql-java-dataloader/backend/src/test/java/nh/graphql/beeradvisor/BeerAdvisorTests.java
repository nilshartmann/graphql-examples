package nh.graphql.beeradvisor;

import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

import static nh.graphql.beeradvisor.Utils.listOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:beeradvisor", "beeradivsor.shop.apiUrl=NOT_CONFIGURED"}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BeerAdvisorTests {


    @Autowired
    GraphQLSchema graphQLSchema;

    @MockBean
    UserService userService;

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

    private static User mockUser(String id) {
        User user = new User(id, "LOGIN_" + id, "NAME_" + id);
        return user;
    }

    @Test
    @Transactional
    public void beerRatingsAuthor() {

        when(userService.findUsersWithId(anyList())).thenAnswer(
            new Answer() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    Object mock = invocation.getMock();
                    return "called with arguments: " + Arrays.toString(args);
                }
            });

        when(userService.getUser(anyString())).thenAnswer(
            new Answer() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    Object mock = invocation.getMock();

                    return mockUser((String) args[0]);

                    // return "called with arguments: " + Arrays.toString(args);
                }
            });

        Map<String, Object> result = query("{\n" +
            "  beer(beerId: \"B1\") {\n" +
            "    ratings {\n" +
            "      author {\n" +
            "        id login name\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n");

        System.out.println("result" + result);

        assertThat(result).hasSize(1);
        assertThat(result).containsKey("data");

        Mockito.verify(userService).getUser("U1");
        Mockito.verify(userService).getUser("U2");
        Mockito.verify(userService).getUser("U3");
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
