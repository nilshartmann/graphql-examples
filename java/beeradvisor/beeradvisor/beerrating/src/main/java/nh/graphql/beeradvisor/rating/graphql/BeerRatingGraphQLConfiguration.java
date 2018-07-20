package nh.graphql.beeradvisor.rating.graphql;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import nh.graphql.beeradvisor.rating.Rating;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class BeerRatingGraphQLConfiguration {

  private final Logger logger = LoggerFactory.getLogger(BeerRatingGraphQLConfiguration.class);

  // @Bean
  // public Publisher<Rating> ratingPublisher() {
  // Observable<Rating> ratingObservable = Observable.create(emitter -> {
  // // AtomicInteger counter = new AtomicInteger(0);

  // // Thread thread = new Thread(() -> {
  // // while (true) {
  // // try {
  // // Thread.sleep(500);
  // // } catch (InterruptedException e) {
  // // throw new RuntimeException(e);
  // // }

  // // // logger.info("EMIT NEW RATING");
  // // emitter.onNext(new Rating(null, null, "R-" + counter.incrementAndGet(),
  // // "CCC", 4));
  // // }
  // // });

  // // thread.setDaemon(true);
  // // thread.start();
  // });

  // ConnectableObservable<Rating> connectableObservable =
  // ratingObservable.share().publish();
  // connectableObservable.connect();

  // final Flowable<Rating> flowable =
  // connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
  // return flowable;
  // }
}
