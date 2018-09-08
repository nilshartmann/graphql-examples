# BeerAdvisor: A graphql-java example with Spring Boot and JPA

The application consists of two Spring Boot Apps:

1. The `shopservice`. This service manages a list of shops (locations and beers they sell). It provides a REST API to query the shops. For simplicity there is no way to modify shops and the actual data is stored in memory only (no database). The purpose of the shop service is to demo, how to access a rest endpoint from GraphQL.

2. The `beeradvisor`. This is the backend for our application. It manages Beers, Ratings and Users and provides a GraphQL API for our domain. It also add the Shops from the `shopservice` to our GraphQL API by connecting to that service via REST.

The third project in this repository is the React-based frontend.

## Configuration

**Ports**

Before starting the services please make sure that the following ports are not in use:

- port **9000** for the `BeerAdvisor` backend
- port **9010** for the `ShopService`
- port **9080** for the frontend (webpack dev server)

**Database**

- When you start the `BeerAdvisor` backend an embedded h2 database will be started,
  that writes it's content to `~/h2/beer-rating`. You can change that in the `application.properties` file.

**Users/Login**

- In order to add Ratings (using GraphQL Mutations) you need to login via the UI. You can simply use any firstname of any author that already has submitted a rating (for example alessa, waldemar, lauren)

## Starting

There are three bash scripts in this folder that helps you starting the application:

1. `01_run_shopservice.sh`
2. `02_run_beeradvisor.sh`
3. `03_run_frontend.sh`

When all three apps are running, you can access the web application at

- `http://localhost:9080`

Alternatively you can run the application using gradle or your IDE:

**Run `shopservice`**

1.  Go to the `shopservice` directory
2.  `./gradlew bootRun`
3.  - OR - Just run `main` method in `ShopServiceApplication` class from your IDE (for example from SpringBoot launcher in IDEA)

**Run `beeradvisor` Application**

1.  Go to the `beeradvisor` directory
2.  `./gradlew bootRun`
3.  - OR - Just run `main` method in `BeerAdvisorApplication` class from your IDE (for example from SpringBoot launcher in IDEA)

**Run the frontend**

1. Go to the `frontend` directory
2. Run `yarn install` (needed only for the first run)
3. Run `yarn start`

# GraphiQL

After running the server, the **GraphiQL API Explorer** is available at `http://localhost:9000/graphiql`

# GraphQL Features in this application

- schema definition with SDL: `rating.graphqls`

- Resolver for root fields (queries): `RatingQueryResolver`

- Resolver for mutations: `RatingMutationResolver`

- Using the Websocket Servlet for Subscriptions via Websocket: `ExampleGraphQLConfiguration.serverEndpointRegistration` (nearly the same as in the spring-boot-starter, only a little simplified for easier understanding)

- Resolver for subscriptions (each new rating is pushed to clients): `RatingSubscriptionResolver`

- Field resolver with arguments: `ShopBeerResolver.ratingsWithStars` (that should be in BeerFieldResolver but does not work due to graphl-java-tools bug: https://github.com/graphql-java/graphql-java-tools/issues/171)

- Extending types and root fields in by a second schema (modulariation of schemas): see `shop` subproject:
  - `shop.graphqls` extends the basic schema from `beerrating`
  - `ShopResolver` adds a field resolver for the `Beer` type from `beerrating` (field `shops`)
  - `ShopResolver.address` adds a field resolver for the `Shop` type, as `address` (defined in the schema) does not exist on the `Shop` java class.

* Security for mutations using Spring Security: `RatingMutationResolver.addRating`

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
