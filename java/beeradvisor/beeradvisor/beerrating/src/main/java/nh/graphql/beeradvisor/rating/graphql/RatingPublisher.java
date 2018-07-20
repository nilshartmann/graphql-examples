package nh.graphql.beeradvisor.rating.graphql;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import nh.graphql.beeradvisor.rating.Rating;

/**
 * RatingPublisher
 */
@Component
public class RatingPublisher implements Publisher<Rating> {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private ObservableEmitter<Rating> emitter;
  private final Flowable<Rating> flowable;

  public RatingPublisher() {
    Observable<Rating> ratingObservable = Observable.create(emitter -> {
      this.emitter = emitter;
    });
    ConnectableObservable<Rating> connectableObservable = ratingObservable.share().publish();
    connectableObservable.connect();

    final Flowable<Rating> flowable = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    this.flowable = flowable;
  }

  @Override
  public void subscribe(Subscriber<? super Rating> s) {
    logger.info("Subscribe from " + s);
    flowable.subscribe(s);
  }

  @TransactionalEventListener
  public void ratingCreated(RatingCreatedEvent ratingCreatedEvent) {
    logger.info("Received RatingCreatedEvent " + ratingCreatedEvent);
    if (this.emitter != null) {
      this.emitter.onNext(ratingCreatedEvent.getRating());
    }
  }

}