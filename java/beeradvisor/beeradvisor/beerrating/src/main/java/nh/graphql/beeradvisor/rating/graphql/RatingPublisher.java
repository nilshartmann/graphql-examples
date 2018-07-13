package nh.graphql.beeradvisor.rating.graphql;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import nh.graphql.beeradvisor.rating.Rating;

/**
 * RatingPublisher
 */
public class RatingPublisher implements Publisher<Rating> {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Flowable<Rating> flowable;

  public RatingPublisher() {
    this.flowable = Flowable.range(0, 1000).map(at -> new Rating(null, null, "R" + at, "CCC", 4));
  }

  @Override
  public void subscribe(Subscriber<? super Rating> s) {
    logger.info("Subscribe from " + s);
    flowable.subscribe(s);
  }

}