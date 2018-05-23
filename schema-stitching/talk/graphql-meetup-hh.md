# Schema Stitching with Apollo GraphQL

GraphQL allows clients to exactly specify what data they need to read and write from an endpoint. But what happens if you have more than one backend you want to query from your client, for example in a microservice architecture? One solution is to "stitch" several schemas from several endpoints into one schema that the client consumes. In this talk I give you a demonstration how schema stitching can be achieved easily with Apollo GraphQL.

* Duration: 25 min plus some minutes for questions

## Agenda

1.  Show example application

    1.  Explain "domain model" with books and reviews, and our architecture
    2.  GraphQL Query: three entities are queries: Books, Reviews and a ProcessInfo for each system
    3.  Show the one-and-only GraphQL query in Apollo Dev Tools or GraphiQL

2.  Show individual schemas from the two backends

* Books: http://localhost:9010/graphiql
* Reviews: http://localhost:9020/graphiql

3.  Show Sticher process:
    1.  Reading and creation of Remote schemas
    2.  Renaming of ping and ProcessInfos
    3.  Extending Books type with reviews field and linking together both Types using an own resolver (`LinkedSchema.ts`)
    4.  Use "regular" `graphqlExpress` for exposing the combined schema via http endpoint

## Preparation:

* Start VS Code
* Run Launcher `01_...`, `02_...` and `03_...`

## TODO

* Customize GraphiQL webapps to better show to what process they belong
* maybe create a ready-to-use fat jar for Spring Boot app
