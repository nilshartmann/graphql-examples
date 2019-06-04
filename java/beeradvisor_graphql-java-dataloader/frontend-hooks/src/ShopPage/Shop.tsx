import React from "react";
import { ShopPageQuery_shop } from "./querytypes/ShopPageQuery";
import styles from "./ShopPage.module.scss";

type ShopProps = {
  shop: ShopPageQuery_shop;
  onBeerClick(beerId: string): void;
};

export default function Shop({ shop, onBeerClick }: ShopProps) {
  return (
    <div className={styles.ShopPage}>
      <div className={styles.DescriptionTitle}>
        <h1>{shop.name}</h1>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ marginRight: "50px" }}>
          <div className={styles.Title}>
            <h1>where to find</h1>
          </div>
          <div>
            <div className={styles.Address}>
              {shop.address.street}
              <br />
              {shop.address.postalCode} {shop.address.city}
              <br />
              {shop.address.country}
            </div>
          </div>
        </div>
        <div className={styles.Title}>
          <h1>what's in stock</h1>

          <div className={styles.Beers}>
            {shop.beers.map(b => (
              <div key={b.id} className={styles.Beer} onClick={() => onBeerClick(b.id)}>
                {b.name}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
