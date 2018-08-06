import * as React from "react";

interface AuthInfoState {
  auth: {
    userId: string;
    username: string;
    authToken: string;
  };
}

interface AuthErrorState {
  error: string;
}

interface AuthProviderState {
  auth: AuthInfoState | AuthErrorState | null;
}

interface IAuthContext {
  auth: AuthInfoState | AuthErrorState | null;
  login(userId: string): void;
}

const { Provider: AuthContextProvider, Consumer: AuthContextConsumer } = React.createContext<IAuthContext>({
  auth: null,
  login() {}
});

class AuthProvider extends React.Component<{}, AuthProviderState> {
  readonly state: AuthProviderState = {
    auth: null
  };

  login = async (userId: string) => {
    const response = await fetch("http://localhost:9000/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=utf-8"
      },
      body: JSON.stringify({
        userId
      })
    });
    if (!response.ok) {
      this.setState({
        auth: {
          error: `Could not authenticate (${response.status})`
        }
      });
      return;
    }
    const json = await response.json();

    this.setState({
      auth: {
        auth: {
          userId,
          username: json.username,
          authToken: json.authToken
        }
      }
    });
  };

  render() {
    return (
      <AuthContextProvider
        value={{
          auth: this.state.auth,
          login: this.login
        }}
      >
        {this.props.children}
      </AuthContextProvider>
    );
  }
}

export { AuthProvider, AuthContextConsumer };
