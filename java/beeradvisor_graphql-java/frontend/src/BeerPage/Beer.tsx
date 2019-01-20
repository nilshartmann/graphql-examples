import * as React from "react";
import * as styles from "./Beer.scss";

import RatingForm from "./RatingForm";

import { BeerPageQuery_beer as BeerData, BeerPageQuery_beer_shops as ShopData } from "./__generated__/BeerPageQuery";
import AddRatingMutation from "./AddRatingMutation";
import Rating from "./Rating";
import { AuthContextConsumer } from "../AuthContext";
import LoginForm from "./LoginForm";
interface ShopProps {
  shop: ShopData;
  onShopClicked: (newCurrentShopId: string) => void;
}

const Shop = ({ shop: { id, name }, onShopClicked }: ShopProps) => (
  <div className={styles.Shop} onClick={() => onShopClicked(id)}>
    <span className={styles.Name}>{name}</span>
  </div>
);

export type SubscribeToMoreFnResult = () => void;
interface BeerProps {
  beer: BeerData;
  subscribeToNewData(): SubscribeToMoreFnResult;
  onShopClicked: (newCurrentShopId: string) => void;
}

export default class Beer extends React.Component<BeerProps> {
  unsubscribeFromNewRatings: SubscribeToMoreFnResult | null = null;

  componentWillReceiveProps(nextProps: BeerProps) {
    if (nextProps.beer.id !== this.props.beer.id) {
      this.unsubscribeFromNewRatings && this.unsubscribeFromNewRatings();
      this.unsubscribeFromNewRatings = nextProps.subscribeToNewData();
    }
  }

  componentDidMount() {
    this.unsubscribeFromNewRatings = this.props.subscribeToNewData();
  }

  componentWillUnmount() {
    this.unsubscribeFromNewRatings && this.unsubscribeFromNewRatings();
  }

  render() {
    const {
      beer: { id, name, price, ratings, shops },
      onShopClicked
    } = this.props;

    return (
      <div className={styles.Beer}>
        <div className={styles.DescriptionTitle}>
          <h1>{name}</h1>
          <h3>{price}</h3>
        </div>
        <div className={styles.Description}>
          <div className={styles.Img}>
            <img src={`/assets/beer/${id}.jpg`} />
          </div>
          <div>
            <div className={styles.Shops}>
              <h1>Where to buy:</h1>
              {shops.map((shop, ix) => (
                <React.Fragment key={shop.id}>
                  <Shop shop={shop} onShopClicked={onShopClicked} />
                  {ix < shops.length - 1 ? " | " : null}
                </React.Fragment>
              ))}
            </div>
            <div className={styles.Ratings}>
              <h1>What customers say:</h1>
              {ratings.map(rating => <Rating key={rating.id} rating={rating} />)}
            </div>

            <h1>
              ...and what do <em>you</em> think?
            </h1>
            <AuthContextConsumer>
              {({ auth, login }) => {
                if (auth === null || "error" in auth) {
                  return <LoginForm login={login} error={auth && auth.error} />;
                }

                return (
                  <AddRatingMutation beerId={id}>
                    {(addNewRating, { error }) => {
                      return (
                        <RatingForm
                          beerId={id}
                          username={auth.auth.username}
                          beerName={name}
                          error={error ? "" + error : null}
                          onNewRating={({ comment, stars }) => {
                            addNewRating({
                              variables: {
                                input: {
                                  userId: auth.auth.userId,
                                  stars: parseInt(stars),
                                  comment,
                                  beerId: id
                                }
                              }
                            });
                          }}
                        />
                      );
                    }}
                  </AddRatingMutation>
                );
              }}
            </AuthContextConsumer>
          </div>
        </div>
      </div>
    );
  }
}
