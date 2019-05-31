import * as React from "react";
import styles from "./Header.module.scss";
import { withRouter, RouteComponentProps } from "react-router";

interface HeaderProps extends RouteComponentProps {
  children?: React.ReactNode;
}

const Header = ({ history }: HeaderProps) => {
  return (
    <header className={styles.Header}>
      <div className={styles.MainHeader}>
        <h1 onClick={() => history.push("/")}>Beer Advisor</h1>
      </div>
    </header>
  );
};

export default withRouter(Header);
