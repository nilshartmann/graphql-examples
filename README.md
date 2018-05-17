# Example: Schema Stitching with Apollo GraphQL

In this example two GraphQL schemas are stitched together to provide a single, consolidated view for the Client.

* App 1 (Java, Spring Boot, graphql-java-tools): Books
* App 2 (JS/TS, graphql-tools): Reviews

Using the schema stitching both schemas are merged together, linked together (Book gets a direct link to its Reviews) and exposed.

