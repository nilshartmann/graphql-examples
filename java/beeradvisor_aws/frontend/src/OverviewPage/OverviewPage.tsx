import * as React from "react";
import gql from "graphql-tag";
import { Query } from "react-apollo";
import { OverviewPageQuery as OverviewPageQueryResult } from "./__generated__/OverviewPageQuery";

import * as styles from "./OverviewPage.scss";
import Stars from "../components";
import { RouteComponentProps } from "react-router";

const OVERVIEW_PAGE_QUERY = gql`
  query OverviewPageQuery {
    beers {
      id
      name
      averageStars
    }
  }
`;

interface OverviewPageProps extends RouteComponentProps {}

export function OverviewPage({ history }: OverviewPageProps) {
  return (
    <div className={styles.BeerOverview}>
      <Query<OverviewPageQueryResult> query={OVERVIEW_PAGE_QUERY}>
        {({ data, error, loading }) => {
          if (error) {
            return <h1>Error while loading Beers 😱</h1>;
          }
          if (loading) {
            return <h1>Please stay tuned - Beers are loading . . .</h1>;
          }
          const { beers } = data!;
          return (
            <>
              {beers.map(beer => (
                <BeerImage
                  key={beer.id}
                  name={beer.name}
                  stars={beer.averageStars}
                  imgUrl={`/assets/beer/${beer.id}-256x256-thumb.jpg`}
                  onClick={() => history.push(`/beer/${beer.id}`)}
                />
              ))}
            </>
          );
        }}
      </Query>
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
      <img src={imgUrl} />
      <span className={styles.Label}>
        <h1>{name}</h1>
        <Stars stars={stars} />
      </span>
    </div>
  );
}

{
  /* <div className={styles.Thumbnail} style={{ backgroundImage: `url(${imgUrl})` }} onClick={onClick} data-tip={name}>
<div className={borderClassName} />
</div> */
}
