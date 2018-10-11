import * as React from "react";
import * as styles from "./Stars.scss";

export default function Stars({ stars }: { stars: number }) {
  const x = new Array(5).fill(undefined).map((_, ix) => (
    <span key={ix} className={ix < stars ? styles.filled : null}>
      ☆
    </span>
  ));
  return <div className={styles.Stars}>{x}</div>;
}
