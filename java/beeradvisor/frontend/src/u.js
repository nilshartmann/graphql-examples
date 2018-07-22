export default class PersonFormController extends React.Component {
  render() {
    const { personId } = this.props;
    return (
      <Query query={GET_PERSON_QUERY} variables={{ personId }}>
        {({ loading, error, data, client }) => {
          // This code is executed, when an existing object in the Cache
          // is modified using the mutation below
          // It is NOT executed, when the mutations ADDS a new object to
          // the cache

          if (loading) {
            return <h1>Loading></h1>;
          }
          if (error) {
            return <h1>Error</h1>;
          }

          return (
            <PersonForm
              onPersonChange={(author, comment) => {
                client.mutate({
                  mutation: UPDATE_PERSON_MUTATION,
                  variables: {
                    personId,
                    name
                  }
                });
              }}
            />
          );
        }}
      </Query>
    );
  }
}
