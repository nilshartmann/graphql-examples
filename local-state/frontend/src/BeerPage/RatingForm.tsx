import * as React from "react";
import * as styles from "./RatingForm.scss";
import { NewRating } from "../types";
import gql from "graphql-tag";
import { Mutation, Query } from "react-apollo";
import { GetDraftRatingQueryResult } from "./__generated__/GetDraftRatingQuery";
import { UpdateDraftRatingMutationResult } from "./__generated__/UpdateDraftRatingMutation";

interface RatingFormControllerProps {
  beerName: string;
  beerId: string;
  onNewRating: (rating: NewRating) => void;
}

const GET_DRAFT_RATING_QUERY = gql`
  query GetDraftRatingQuery($beerId: ID!) {
    draft: draftRatingForBeer(beerId: $beerId) @client {
      author
      comment
    }
  }
`;

const UPDATE_DRAFT_RATING = gql`
  mutation UpdateDraftRatingMutation($beerId: ID!, $author: String!, $comment: String!) {
    setDraftRatingForBeer(beerId: $beerId, author: $author, comment: $comment) @client {
      author
      comment
    }
  }
`;

export default class RatingFormController extends React.Component<RatingFormControllerProps> {
  render() {
    const { beerId } = this.props;
    return (
      <Query<GetDraftRatingQueryResult> query={GET_DRAFT_RATING_QUERY} variables={{ beerId }}>
        {({ loading, error, data, client }) => {
          if (loading) {
            return <h1>Loading></h1>;
          }
          if (error) {
            return <h1>Error</h1>;
          }

          const { author = "", comment = "" } = data!.draft || { author: "", comment: "" };

          return (
            <RatingForm
              onRatingChange={(author, comment) => {
                client.mutate<UpdateDraftRatingMutationResult>({
                  mutation: UPDATE_DRAFT_RATING,
                  variables: {
                    beerId,
                    author,
                    comment
                  }
                });
              }}
              onNewRating={this.props.onNewRating}
              beerId={this.props.beerId}
              beerName={this.props.beerName}
              author={author}
              comment={comment}
            />
          );
        }}
      </Query>
    );
  }
}

interface RatingFormProps {
  beerName: string;
  beerId: string;
  author: string;
  comment: string;
  onRatingChange: (author: string, comment: string) => void;
  onNewRating: (rating: NewRating) => void;
}
class RatingForm extends React.Component<RatingFormProps> {
  authorElement: HTMLInputElement | null = null;
  commentElement: HTMLInputElement | null = null;

  onFormChange() {
    const { beerId, onRatingChange } = this.props;
    const author = this.authorElement!.value;
    const comment = this.commentElement!.value;

    onRatingChange(author, comment);
  }

  onAuthorChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.onFormChange();
    // console.log("uathorEleent", this.authorElement!.value);
    // // this.setState({ author: e.currentTarget.value });
  };

  onCommentChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
    this.onFormChange();
    // this.setState({ comment: e.currentTarget.value });
  };

  setAuthorElementRef = (ref: HTMLInputElement | null) => (this.authorElement = ref);
  setCommentElementRef = (ref: HTMLInputElement | null) => (this.commentElement = ref);

  onLeaveRatingClick = (e: React.SyntheticEvent<HTMLButtonElement>) => {
    e.preventDefault();

    // // const { author, comment } = this.state;
    // const { onNewRating } = this.props;

    // onNewRating({ author, comment });
    // this.setState({
    //   author: "",
    //   comment: ""
    // });
  };

  render() {
    const { beerName, beerId, author, comment } = this.props;
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
              <input
                ref={this.setAuthorElementRef}
                type="text"
                id={`ratingform-name-${beerId}`}
                value={author}
                onChange={this.onAuthorChange}
              />
            </div>
            <div>
              <label htmlFor={`ratingform-comment-${beerId}`}>Your rating:</label>{" "}
              <input
                ref={this.setCommentElementRef}
                type="text"
                id={`ratingform-comment-${beerId}`}
                value={comment}
                onChange={this.onCommentChange}
              />
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
