import * as React from "react";
import * as styles from "./Beer.scss";

import RatingForm from "./RatingForm";

import { BeerPageQueryResult_beer as BeerData, BeerPageQueryResult_beer_shops as ShopData } from "./__generated__/BeerPageQuery";
import AddRatingMutation from "./AddRatingMutation";
import Rating from "./Rating";
import { AuthContextConsumer } from "../AuthContext";
import LoginForm from "./LoginForm";
interface ShopProps {
  shop: ShopData;
}

const Shop = ({ shop: { id, name } }: ShopProps) => (
  <div className={styles.Shop}>
    <span className={styles.Name}>{name}</span>
  </div>
);

interface BeerProps {
  beer: BeerData;
}

export default function Beer({ beer: { id, name, price, ratings, shops } }: BeerProps) {
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
                <Shop shop={shop} />
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
