import * as React from "react";
import styles from "./Stars.module.scss";

export default function Stars({ stars }: { stars: number }) {
  const x = new Array(5).fill(undefined).map((_, ix) => (
    <span key={ix} className={ix < stars ? styles.filled : undefined}>
      ☆
    </span>
  ));
  return <div className={styles.Stars}>{x}</div>;
}
