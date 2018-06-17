import * as React from "react";
import * as styles from "./RatingForm.scss";
import { NewRating } from "../types";
import gql, { default as clientGql } from "graphql-tag";
import { Query } from "react-apollo";
import { GetDraftRatingQueryResult } from "./__generated__/GetDraftRatingQuery";
import { UpdateDraftRatingMutationResult } from "./__generated__/UpdateDraftRatingMutation";

interface RatingFormControllerProps {
  beerName: string;
  beerId: string;
  onNewRating: (rating: NewRating) => void;
}

// without fetching the id in the query below, the refetchQueries does not work?!
const GET_DRAFT_RATING_QUERY = gql`
  query GetDraftRatingQuery($beerId: ID!) {
    draft: draftRatingForBeer(beerId: $beerId) @client {
      id
      author
      comment
    }
  }
`;

const BEERS_QUERY = gql`
  query BeersQuery {
    beers {
      id
      hasDraftRating @client
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
                  },
                  // without refetchQuery the Query doesn't get updated, if
                  // the first execution of the query returns null
                  // see: https://www.apollographql.com/docs/react/advanced/caching.html#after-mutations
                  // NOTE: ATTENTION: Refetching BEERS_QUERY leads to server request,
                  // as the query contains also remote fields! Not a valid solutions for 'real' apps
                  refetchQueries: [{ query: GET_DRAFT_RATING_QUERY, variables: { beerId } }, { query: BEERS_QUERY }]
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

  getCurrentFormValues() {
    return {
      beerId: this.props,
      author: this.authorElement!.value,
      comment: this.commentElement!.value
    };
  }

  setAuthorElementRef = (ref: HTMLInputElement | null) => (this.authorElement = ref);
  setCommentElementRef = (ref: HTMLInputElement | null) => (this.commentElement = ref);

  onFormChange = () => {
    const { onRatingChange } = this.props;
    const { author, comment } = this.getCurrentFormValues();

    onRatingChange(author, comment);
  };

  onLeaveRatingClick = (e: React.SyntheticEvent<HTMLButtonElement>) => {
    e.preventDefault();

    this.props.onNewRating(this.getCurrentFormValues());

    // reset form
    this.props.onRatingChange("", "");
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
                onChange={this.onFormChange}
              />
            </div>
            <div>
              <label htmlFor={`ratingform-comment-${beerId}`}>Your rating:</label>{" "}
              <input
                ref={this.setCommentElementRef}
                type="text"
                id={`ratingform-comment-${beerId}`}
                value={comment}
                onChange={this.onFormChange}
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
