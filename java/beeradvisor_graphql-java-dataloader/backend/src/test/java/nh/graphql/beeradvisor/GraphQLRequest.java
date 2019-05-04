package nh.graphql.beeradvisor;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class GraphQLRequest {

    private String query;

    public GraphQLRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
