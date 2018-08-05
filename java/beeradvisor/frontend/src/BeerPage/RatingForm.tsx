import * as React from "react";
import * as styles from "./RatingForm.scss";
import { NewRating } from "../types";
import { UserContexConsumer } from "../UserProvider";

interface RatingFormProps {
  beerName: string;
  beerId: string;
  onNewRating: (rating: NewRating) => void;
}

interface RatingFormState extends NewRating {}

export default class RatingForm extends React.Component<RatingFormProps, RatingFormState> {
  readonly state: RatingFormState = {
    author: "",
    comment: "",
    stars: ""
  };

  onAuthorChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ author: e.currentTarget.value });
  };

  onCommentChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ comment: e.currentTarget.value });
  };

  onStarsChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ stars: e.currentTarget.value });
  };

  onLeaveRatingClick = (e: React.SyntheticEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const { author, comment, stars } = this.state;
    const { onNewRating } = this.props;

    onNewRating({ author, comment, stars });
    this.setState({
      author: "",
      comment: "",
      stars: ""
    });
  };

  render() {
    const { beerName, beerId } = this.props;
    const { author, comment, stars } = this.state;

    const buttonEnabled = !!author && !!comment;

    return (
      <UserContexConsumer>
        {({ setCurrentUserId, currentUserId }) => (
          <div className={styles.RatingForm}>
            <h1>
              ...and what do <em>you</em> think?
            </h1>
            <form>
              <fieldset>
                <div>
                  <label>Your name:</label> <input type="text" value={currentUserId || ""} onChange={this.onAuthorChange} />
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
              </fieldset>
            </form>
            <button onClick={() => setCurrentUserId(comment)}>user</button>
          </div>
        )}
      </UserContexConsumer>
    );
  }
}
