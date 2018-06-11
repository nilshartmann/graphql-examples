import * as React from "react";
import * as styles from "./BeerRack.scss";
interface BeerRackProps {
  beerIds: { id: string }[];
  onBeerSelected: (beerId: string) => void;
}

export default function BeerRack({ beerIds, onBeerSelected }: BeerRackProps) {
  return (
    <div className={styles.BeerRack}>
      {beerIds.map(beerId => (
        <div
          key={beerId.id}
          className={styles.Thumbnail}
          style={{ backgroundImage: `url('/assets/beer/${beerId.id}.jpg')` }}
          onClick={() => onBeerSelected(beerId.id)}
        />
      ))}
    </div>
  );
}
