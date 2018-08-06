import * as React from "react";
import * as styles from "./RatingForm.scss";

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

  onLoginClick = (e: React.SyntheticEvent<HTMLButtonElement>) => {
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
      <div className={styles.RatingForm}>
        <h1>Please login first</h1>
        <form>
          <fieldset>
            <div>
              <label>Your userId:</label> <input type="text" value={userId} onChange={this.onUserIdChange} />
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
