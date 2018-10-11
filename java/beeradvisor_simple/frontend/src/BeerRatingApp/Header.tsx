import * as React from "react";
import * as styles from "./Header.scss";
import { withRouter, RouteComponentProps } from "react-router";

interface HeaderProps extends RouteComponentProps {
  children?: React.ReactNode;
}

const Header = ({ history }: HeaderProps) => {
  return (
    <header className={styles.Header}>
      <div className={styles.MainHeader}>
        <h1 onClick={() => history.push("/")}>Beer Advisor</h1>
        <h2 />
      </div>
    </header>
  );
};

export default withRouter(Header);
