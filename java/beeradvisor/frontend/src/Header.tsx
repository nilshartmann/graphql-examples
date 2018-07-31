import * as React from "react";
import * as styles from "./Header.scss";

interface HeaderProps {
  children?: React.ReactNode;
}

const Header = ({ children }: HeaderProps) => (
  <header className={styles.Header}>
    <div className={styles.MainHeader}>
      <h1>Beer Advisor</h1>
      <h2 />
    </div>
  </header>
);

export default Header;
