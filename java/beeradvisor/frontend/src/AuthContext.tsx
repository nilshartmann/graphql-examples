import * as React from "react";

interface AuthInfoState {
  auth: {
    userId: string;
    username: string;
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

const getAuthToken = () => sessionStorage.getItem("auth-token");
const setAuthToken = (authToken: string | null) =>
  authToken ? sessionStorage.setItem("auth-token", authToken) : sessionStorage.removeItem("auth-token");

class AuthProvider extends React.Component<{}, AuthProviderState> {
  readonly state: AuthProviderState = {
    auth: null
  };

  login = async (loginId: string) => {
    setAuthToken(null);
    const response = await fetch("http://localhost:9000/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=utf-8"
      },
      body: JSON.stringify({
        login: loginId
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

    setAuthToken(json.authToken);

    this.setState({
      auth: {
        auth: {
          userId: json.userId,
          username: json.username
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

export { AuthProvider, AuthContextConsumer, getAuthToken };
