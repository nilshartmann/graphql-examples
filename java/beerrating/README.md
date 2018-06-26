# Graphql-Java with JPA Example

**+++ WORK IN PROGRESS +++**

## Run

**Setup Database**

* Either run the `docker/postgres-compose.yaml` file to start a local postgres database

or 

* Modify `src/main/resources/application.properties` for an embedded H2 database (see comments there)  


**Run Application**

* Either `gradlew bootRun`

or

* Just run `main` method in `BeerRatingApplication` class (for example from SpringBoot launcher in IDEA)

# Test Queries

For "testing" the JPA/hibernate behaviour, there are three HTTP endpoints (all GET):

* `http://localhost:9000/gql`: runs a simple GraphQL query

* `http://localhost:9000/beers`: runs a simple "findAll" JPA Query (without optimizations, i.e. all Relations are LAZY) (n DB Queries needed for all requested objects)

* `http://localhost:9000/fetchbeers`: runs a JPA query with an EntityGraph configured to fetch both Beer and Ratings in ONE query (for that "use case" only those two domain objects are needed)

* `http://localhost:9000/fetchall`: runs a JPA query with an EntityGraph consisting of all three Domain objects (i.e. ONE database query for whole object model, that is needed in that "use case")

You can see the actual queries that Hibernate runs on the console.

# Classes

* HTTP Endpoints: `BeerController`

* JPA Queries: `BeerRepository`

* "domain" objects: `model` package

* GraphQL Schema: `beers.graphqls`
* Resolver and Schema creations: `graphql` package