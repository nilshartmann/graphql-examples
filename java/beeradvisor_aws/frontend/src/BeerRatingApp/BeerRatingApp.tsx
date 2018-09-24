import * as React from "react";

import * as styles from "./BeerRatingApp.scss";
import Header from "./Header";
import BeerPage from "../BeerPage";
import BeerRack from "../BeerRack";
import Footer from "./Footer";
import { OverviewPage } from "../OverviewPage";

interface BeerRatingAppState {
  currentBeerId: string;
}
class BeerRatingApp extends React.Component<{}, BeerRatingAppState> {
  readonly state: BeerRatingAppState = {
    currentBeerId: "B1"
  };

  setCurrentBeerId = (newCurrentBeerId: string) => {
    this.setState({
      currentBeerId: newCurrentBeerId
    });
  };

  render() {
    const { currentBeerId } = this.state;
    return (
      <div className={styles.BeerRatingApp}>
        <Header />
        <div className={styles.Main}>
          <OverviewPage />
          {/* <BeerRack currentBeerId={currentBeerId} setCurrentBeerId={this.setCurrentBeerId} />
          <BeerPage beerId={currentBeerId} /> */}
        </div>
        <Footer />
      </div>
    );
  }
}

export default BeerRatingApp;
