import * as React from "react";
import gql from "graphql-tag";
import { Query } from "@apollo/react-components";
import { OverviewPageQuery as OverviewPageQueryResult } from "./querytypes/OverviewPageQuery";

import styles from "./OverviewPage.module.scss";
import Stars from "../components";
import { RouteComponentProps } from "react-router";
import { useQuery } from "@apollo/react-hooks";

const OVERVIEW_PAGE_QUERY = gql`
  query OverviewPageQuery {
    beers {
      id
      name
      averageStars
    }
  }
`;

type OverviewPageProps = RouteComponentProps;

export function OverviewPage({ history }: OverviewPageProps) {
  const { loading, error, data } = useQuery(OVERVIEW_PAGE_QUERY);

  if (error) {
    return <h1>Error while loading Beers</h1>;
  }
  if (loading) {
    return <h1>Please stay tuned - Beers are loading . . .</h1>;
  }

  const { beers } = data!;
  return (
    <div className={styles.BeerOverview}>
      {beers.map((beer: any) => (
        <BeerImage
          key={beer.id}
          name={beer.name}
          stars={beer.averageStars}
          imgUrl={`/assets/beer/${beer.id}-256x256-thumb.jpg`}
          onClick={() => history.push(`/beer/${beer.id}`)}
        />
      ))}
    </div>
  );
}

interface ThumbnailProps {
  imgUrl: string;
  name: string;
  stars: number;
  onClick: () => void;
  active?: boolean;
}

function BeerImage({ imgUrl, name, onClick, stars }: ThumbnailProps) {
  return (
    <div className={styles.BeerImage} onClick={onClick}>
      <img alt={name} src={imgUrl} />
      <span className={styles.Label}>
        <h1>{name}</h1>
        <Stars stars={stars} />
      </span>
    </div>
  );
}
