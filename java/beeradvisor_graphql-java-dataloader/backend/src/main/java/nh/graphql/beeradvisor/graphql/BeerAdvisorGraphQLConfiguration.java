package nh.graphql.beeradvisor.graphql;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import nh.graphql.beeradvisor.auth.graphql.LoginDataFetchers;
import nh.graphql.beeradvisor.graphql.fetchers.BeerAdvisorDataFetcher;
import nh.graphql.beeradvisor.graphql.fetchers.BeerDataFetchers;
import nh.graphql.beeradvisor.graphql.fetchers.RatingDataFetchers;
import nh.graphql.beeradvisor.graphql.fetchers.ShopFieldDataFetchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.InputStreamReader;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeerAdvisorGraphQLConfiguration {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BeerAdvisorDataFetcher beerAdvisorDataFetcher;
    @Autowired
    private BeerDataFetchers beerDataFetchers;
    @Autowired
    private ShopFieldDataFetchers shopFieldDataFetchers;
    @Autowired
    private LoginDataFetchers loginDataFetchers;
    @Autowired
    private RatingDataFetchers ratingDataFetchers;

    @Bean
    public GraphQLSchema graphQLSchema() {
        SchemaParser schemaParser = new SchemaParser();

        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        typeRegistry.merge(schemaParser.parse(schemaReader("auth")));
        typeRegistry.merge(schemaParser.parse(schemaReader("rating")));
        typeRegistry.merge(schemaParser.parse(schemaReader("shop")));

        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        final RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type(newTypeWiring("Query")
                .dataFetcher("ping", beerAdvisorDataFetcher.pingFetcher())
                .dataFetcher("beer", beerAdvisorDataFetcher.beerFetcher())
                .dataFetcher("beers", beerAdvisorDataFetcher.beersFetcher())
                .dataFetcher("shops", beerAdvisorDataFetcher.shopsFetcher())
                .dataFetcher("shop", beerAdvisorDataFetcher.shopFetcher()))
            .type(newTypeWiring("Beer")
                .dataFetcher("averageStars", beerDataFetchers.averageStarsFetcher())
                .dataFetcher("ratingsWithStars", beerDataFetchers.ratingsWithStarsFetcher())
                .dataFetcher("shops", beerDataFetchers.shopsFetcher()))
            .type(newTypeWiring("Shop")
                .dataFetcher("address", shopFieldDataFetchers.addressFetcher())
                .dataFetcher("beers", shopFieldDataFetchers.beersFetcher()))
            .type(newTypeWiring("Rating")
                .dataFetcher("author", ratingDataFetchers.authorFetcher()))
            .type(newTypeWiring("Mutation")
                .dataFetcher("login", loginDataFetchers.getLoginDataFetcher())
                .dataFetcher("addRating", beerAdvisorDataFetcher.addRatingMutationFetcher())
				.dataFetcher("updateBeerName", beerAdvisorDataFetcher.updateBeerNameFetcher()))
            .type(newTypeWiring("Subscription")
                .dataFetcher("newRatings", beerAdvisorDataFetcher.newRatingsSubscriptionFetcher())
                .dataFetcher("onNewRating", beerAdvisorDataFetcher.onNewRatingSubscriptionFetcher())
            )
            .build();

        return runtimeWiring;
    }

    private InputStreamReader schemaReader(String schemaName) {
        String schemaPath = String.format("/schema/%s.graphqls", schemaName);
        InputStream stream = BeerAdvisorGraphQLConfiguration.class.getResourceAsStream(schemaPath);
        if (stream == null) {
            throw new IllegalStateException("Could not find schema file: " + schemaPath);
        }

        return new InputStreamReader(stream);
    }

}
