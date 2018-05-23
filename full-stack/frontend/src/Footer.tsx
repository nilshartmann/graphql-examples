import * as React from "react";
import * as styles from "./Footer.scss";
interface FooterProps {
  children?: React.ReactNode;
}
const Footer = ({ children }: FooterProps) => <footer className={styles.Footer}>&nbsp;</footer>;

export default Footer;
