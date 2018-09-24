import * as React from "react";

import * as styles from "./BeerRatingApp.scss";
import Header from "./Header";
import BeerPage from "../BeerPage";
import Footer from "./Footer";
import { OverviewPage } from "../OverviewPage";
import ShopPage from "../ShopPage";

interface BeerRatingAppState {
  currentBeerId: string | null;
  currentShopId: string | null;
}
class BeerRatingApp extends React.Component<{}, BeerRatingAppState> {
  readonly state: BeerRatingAppState = {
    currentBeerId: null,
    currentShopId: null
  };

  setCurrentBeerId = (newCurrentBeerId: string) => {
    this.setState({
      currentBeerId: newCurrentBeerId,
      currentShopId: null
    });
  };

  setCurrentShopId = (newCurrentShopId: string) => {
    this.setState({
      currentBeerId: null,
      currentShopId: newCurrentShopId
    });
  };

  render() {
    const { currentBeerId, currentShopId } = this.state;
    return (
      <div className={styles.BeerRatingApp}>
        <Header />
        <div className={styles.Main}>
          {currentShopId ? (
            <ShopPage shopId={currentShopId} onBeerClicked={this.setCurrentBeerId} />
          ) : currentBeerId ? (
            <BeerPage beerId={currentBeerId} onShopClicked={this.setCurrentShopId} />
          ) : (
            <OverviewPage onBeerClicked={this.setCurrentBeerId} />
          )}
        </div>
        <Footer />
      </div>
    );
  }
}

export default BeerRatingApp;
