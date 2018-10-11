import * as React from "react";
import * as styles from "./Form.scss";
import { NewRating } from "../types";

interface RatingFormProps {
  beerName: string;
  beerId: string;
  username: string;
  error?: string | null;
  onNewRating: (rating: NewRating) => void;
}

interface RatingFormState {
  comment: string;
  stars: string;
}

export default class RatingForm extends React.Component<RatingFormProps, RatingFormState> {
  readonly state: RatingFormState = {
    comment: "",
    stars: ""
  };

  onCommentChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ comment: e.currentTarget.value });
  };

  onStarsChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ stars: e.currentTarget.value });
  };

  onLeaveRatingClick = (e: React.SyntheticEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const { comment, stars } = this.state;
    const { onNewRating } = this.props;

    onNewRating({ comment, stars });
    this.setState({
      comment: "",
      stars: ""
    });
  };

  render() {
    const { beerName, username, error } = this.props;
    const { comment, stars } = this.state;

    const buttonEnabled = !!stars && !!comment;

    return (
      <div className={styles.Form}>
        <form>
          <fieldset>
            <div>
              <label>Your name:</label> <input type="text" value={username} readOnly />
            </div>
            <div>
              <label>Your rating (1-5):</label>{" "}
              <input type="number" min="1" max="5" value={stars} onChange={this.onStarsChange} />
            </div>
            <div>
              <label>Your comment:</label> <input type="text" value={comment} onChange={this.onCommentChange} />
            </div>
            <div>
              <button disabled={!buttonEnabled} onClick={this.onLeaveRatingClick}>
                Leave rating for {beerName}
              </button>
            </div>
            {error && (
              <div>
                <b>Could not add rating:</b> {error}
              </div>
            )}
          </fieldset>
        </form>
      </div>
    );
  }
}
