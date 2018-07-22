import * as React from "react";
import * as styles from "./Header.scss";

interface HeaderProps {
  children?: React.ReactNode;
}

const Header = ({ children }: HeaderProps) => (
  <header className={styles.Header}>
    {/* <div className={styles.UpperHeader}>{children}</div> */}
    <div className={styles.MainHeader}>
      <h1>Beer Rating</h1>
      <h2 />
    </div>
  </header>
);

export default Header;
