import * as React from "react";
import * as styles from "./Form.scss";

interface LoginFormProps {
  error: string | null;
  login: (userId: string) => void;
}

interface LoginFormState {
  userId: string;
}

export default class LoginForm extends React.Component<LoginFormProps, LoginFormState> {
  readonly state: LoginFormState = {
    userId: ""
  };

  onUserIdChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ userId: e.currentTarget.value });
  };

  onLoginClick = (e: React.SyntheticEvent<HTMLElement>) => {
    e.preventDefault();

    const { userId } = this.state;
    const { login } = this.props;

    login(userId);
  };

  render() {
    const { error } = this.props;
    const { userId } = this.state;

    const buttonEnabled = !!userId;

    return (
      <div className={styles.Form}>
        <div className={styles.Hint}>Please login first</div>
        <form>
          <fieldset>
            <div>
              <label>Your login:</label>{" "}
              <input
                type="text"
                value={userId}
                onChange={this.onUserIdChange}
                onKeyPress={e => (e.keyCode === 13 ? this.onLoginClick(e) : null)}
              />
            </div>
            {error && <div>Could not login: {error}</div>}
            <div>
              <button disabled={!buttonEnabled} onClick={this.onLoginClick}>
                Login
              </button>
            </div>
          </fieldset>
        </form>
      </div>
    );
  }
}
