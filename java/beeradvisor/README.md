# Graphql-Java with Spring Boot and JPA Example

_+++ WORK IN PROGRESS +++_

## Run

**Setup and start database**

- Either run the `docker/postgres-compose.yaml` file to start a local postgres database

or

- Modify `app/src/main/resources/application.properties` for an embedded H2 database (see comments there)

**Run `shopservice`**
**Note:** please make sure that **port 9010** is not in use. Otherwise set another port in the `application.properties` of the `shopservice` project.

1.  Go to the `shopservice` directory
2.  `./gradlew bootRun`
3.  - OR - Just run `main` method in `ShopServiceApplication` class from your IDE (for example from SpringBoot launcher in IDEA)

**Run `beeradvisor` Application**

**Note:** please make sure that **port 9000** is not in use. Otherwise set another port in the `application.properties` of `beeradvisor` project.

1.  Go to the `beeradvisor` directory
2.  `./gradlew bootRun`
3.  - OR - Just run `main` method in `BeerAdvisorApplication` class from your IDE (for example from SpringBoot launcher in IDEA)

# GraphiQL

After running the server, the **GraphiQL API Explorer** is available at `http://localhost:9000/graphiql`

# Test Queries

For "testing" the JPA/hibernate behaviour, there are three HTTP endpoints (all GET):

- `http://localhost:9000/gql`: runs GraphQL query that needs all three domain objects

- `http:localhost:9000/gql/ratings`: GraphQL query that only needs beer and ratings, the JPA query is optimized to only fetch those two entities from DB

- `http:localhost:9000/gql/beers`: GraphQL query that only needs beer, the JPA query is optimized to only fetch the Beer entity from DB

- `http:localhost:9000/gql/shops`: GraphQL query that loads the shops (another "domain")

- `http://localhost:9000/beers`: runs a simple "findAll" JPA Query (without optimizations, i.e. all Relations are LAZY) (n DB Queries needed for all requested objects)

- `http://localhost:9000/fetchbeers`: runs a JPA query with an EntityGraph configured to fetch both Beer and Ratings in ONE query (for that "use case" only those two domain objects are needed)

- `http://localhost:9000/fetchall`: runs a JPA query with an EntityGraph consisting of all three Domain objects (i.e. ONE database query for whole object model, that is needed in that "use case")

You can see the actual queries that Hibernate runs on the console.

# Architecture

The project contains of two apps:

- `shopservice`: A little "microservice", that knows a list of `Shop`s and what `Beer` they sell. The `shopservice` provides a REST endpoint for requesting shops.

- `beeradivsor`: the "real" application that provides `Beer`, `Rating` and the GraphQL API

The `beeradivsor` project is setup as a multi module gradle project that consists of three Gradle modules:

- `beerrating`: this is our "core" domain. It consists of the Beer and Rating entities

- `shop`: this is the GraphQL "gateway" to the `shopservice`

- `app`: this contains all necessary infrastructure code and configuration. It also configures the GraphQL servlet.

Both of the "domain" modules ship their own GraphQL API (so they are complete verticals), while "beerrating" is the base API that is extended
by the "shop" module with the shop-specific types and queries. One could use the beerrating API without the shop api.

The third module is the Spring Boot Application module:

## graphql-spring-boot-starter vs custom configuration

For demonstration purposes that project by default does not use the `graphiql-spring-boot-starter` to configure
the GraphQL schema and servlet.

Instead the configuration is manually done in `ExampleGraphQLConfiguration` inside the `app` module.

To use the starter project instead:

1.  remove the `@Configuration` annotation from `ExampleGraphQLConfiguration`
2.  add the starter module to the gradle dependencies (`app/build.gradle`): `compile compile 'com.graphql-java:graphql-spring-boot-starter:4.2.0'`
