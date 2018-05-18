import * as React from "react";

interface FooterProps {
  children?: React.ReactNode;
}
const Footer = ({ children }: FooterProps) => <footer>{children}</footer>;

export default Footer;
