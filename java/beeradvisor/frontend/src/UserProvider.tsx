import * as React from "react";

interface IUserContext {
  currentUserId: string | null;
  setCurrentUserId(newCurrentUserId: string | null): void;
}

interface UserProviderState {
  currentUserId: string | null;
}

const UserContext = React.createContext<IUserContext>({
  currentUserId: null,
  setCurrentUserId() {}
});

class UserProvider extends React.Component<{}, UserProviderState> {
  readonly state: UserProviderState = {
    currentUserId: null
  };

  setCurrentUserId = (newUserId: string | null) => {
    this.setState({
      currentUserId: newUserId
    });
  };

  render() {
    return (
      <UserContext.Provider
        value={{
          currentUserId: this.state.currentUserId,
          setCurrentUserId: this.setCurrentUserId
        }}
      >
        {this.props.children}
      </UserContext.Provider>
    );
  }
}

const UserContexConsumer = UserContext.Consumer;

export { UserProvider, UserContexConsumer };
