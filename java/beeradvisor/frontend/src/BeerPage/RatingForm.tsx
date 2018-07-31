import * as React from "react";
import * as styles from "./RatingForm.scss";
import { NewRating } from "../types";

interface RatingFormProps {
  beerName: string;
  beerId: string;
  onNewRating: (rating: NewRating) => void;
}

interface RatingFormState extends NewRating {}

export default class RatingForm extends React.Component<RatingFormProps, RatingFormState> {
  readonly state: RatingFormState = {
    author: "",
    comment: ""
  };

  onAuthorChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ author: e.currentTarget.value });
  };

  onCommentChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.setState({ comment: e.currentTarget.value });
  };

  onLeaveRatingClick = (e: React.SyntheticEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const { author, comment } = this.state;
    const { onNewRating } = this.props;

    onNewRating({ author, comment });
    this.setState({
      author: "",
      comment: ""
    });
  };

  render() {
    const { beerName, beerId } = this.props;
    const { author, comment } = this.state;

    const buttonEnabled = !!author && !!comment;

    return (
      <div className={styles.RatingForm}>
        <h1>
          ...and what do <em>you</em> think?
        </h1>
        <form>
          <fieldset>
            <div>
              <label htmlFor={`ratingform-name-${beerId}`}>Your name:</label>{" "}
              <input type="text" id={`ratingform-name-${beerId}`} value={author} onChange={this.onAuthorChange} />
            </div>
            <div>
              <label htmlFor={`ratingform-comment-${beerId}`}>Your rating:</label>{" "}
              <input type="text" id={`ratingform-comment-${beerId}`} value={comment} onChange={this.onCommentChange} />
            </div>
            <div>
              <button disabled={!buttonEnabled} onClick={this.onLeaveRatingClick}>
                Leave rating for {beerName}
              </button>
            </div>
          </fieldset>
        </form>
      </div>
    );
  }
}
