import React from "react";
import styles from "./Beer.module.scss";

import RatingForm from "./RatingForm";

import { BeerPageQuery_beer as BeerData, BeerPageQuery_beer_shops as ShopData } from "./querytypes/BeerPageQuery";
import AddRating from "./AddRating";
import Rating from "./Rating";
import { useAuthContext } from "../AuthContext";
import LoginForm from "./LoginForm";
type ShopProps = {
  shop: ShopData;
  onShopClicked: (newCurrentShopId: string) => void;
};

const Shop = ({ shop: { id, name }, onShopClicked }: ShopProps) => (
  <div className={styles.Shop} onClick={() => onShopClicked(id)}>
    <span className={styles.Name}>{name}</span>
  </div>
);

export type SubscribeToMoreFnResult = () => void;
type BeerProps = {
  beer: BeerData;
  subscribeToNewData(): SubscribeToMoreFnResult;
  onShopClicked: (newCurrentShopId: string) => void;
};

export default function Beer(props: BeerProps) {
  const {
    beer: { id, name, price, ratings, shops },
    onShopClicked,
    subscribeToNewData
  } = props;

  const { auth, login } = useAuthContext();

  React.useEffect(() => {
    const unsubscribeFromNewRatings = subscribeToNewData();
    return unsubscribeFromNewRatings;
  }, [id, subscribeToNewData]);

  return (
    <div className={styles.Beer}>
      <div className={styles.DescriptionTitle}>
        <h1>{name}</h1>
        <h3>{price}</h3>
      </div>
      <div className={styles.Description}>
        <div className={styles.Img}>
          <img alt={name} src={`/assets/beer/${id}.jpg`} />
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
            {ratings.map(rating => (
              <Rating key={rating.id} rating={rating} />
            ))}
          </div>

          <h1>
            ...and what do <em>you</em> think?
          </h1>
          {auth === null || "error" in auth ? (
            <LoginForm login={login} error={auth && auth.error} />
          ) : (
            <AddRating beerId={id}>
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
            </AddRating>
          )}
        </div>
      </div>
    </div>
  );
}
