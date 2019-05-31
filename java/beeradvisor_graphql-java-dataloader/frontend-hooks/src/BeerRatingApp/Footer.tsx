import * as React from "react";
import styles from "./Footer.module.scss";

const GITHUB_REPO = "https://github.com/nilshartmann/graphql-examples";

const Footer = () => (
  <footer className={styles.Footer}>
    <a href={GITHUB_REPO} target="_blank" rel="noopener noreferrer">
      {GITHUB_REPO}
    </a>
  </footer>
);

export default Footer;
