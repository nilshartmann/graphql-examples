import * as React from "react";

import { BrowserRouter as Router, Route } from "react-router-dom";

import * as styles from "./BeerRatingApp.scss";
import Header from "./Header";
import BeerPage from "../BeerPage";
import Footer from "./Footer";
import { OverviewPage } from "../OverviewPage";
import ShopPage from "../ShopPage";

class BeerRatingApp extends React.Component {
  render() {
    return (
      <Router>
        <div className={styles.BeerRatingApp}>
          <Header />
          <div className={styles.Main}>
            <Route exact path="/" component={OverviewPage} />
            <Route path="/beer/:beerId" component={BeerPage} />
            <Route path="/shop/:shopId" component={ShopPage} />
          </div>
          <Footer />
        </div>
      </Router>
    );
  }
}

export default BeerRatingApp;
