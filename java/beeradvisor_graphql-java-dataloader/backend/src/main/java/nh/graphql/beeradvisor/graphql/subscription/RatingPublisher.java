package nh.graphql.beeradvisor.graphql.subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import nh.graphql.beeradvisor.domain.Rating;
import nh.graphql.beeradvisor.domain.RatingCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * RatingPublisher
 */
@Component
public class RatingPublisher {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private ObservableEmitter<Rating> emitter;
  private final Flowable<Rating> publisher;

  public RatingPublisher() {
    Observable<Rating> ratingObservable = Observable.create(emitter -> {
      this.emitter = emitter;
    });
    ConnectableObservable<Rating> connectableObservable = ratingObservable.share().publish();
    connectableObservable.connect();

    this.publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
  }

  @TransactionalEventListener
  public void ratingCreated(RatingCreatedEvent ratingCreatedEvent) {
    logger.info("Received RatingCreatedEvent " + ratingCreatedEvent);
    if (this.emitter != null) {
      this.emitter.onNext(ratingCreatedEvent.getRating());
    }
  }

  public Flowable<Rating> getPublisher() {
    return this.publisher;
  }

  public Flowable<Rating> getPublisher(String beerId) {
    return this.publisher.filter(rating -> beerId.equals(rating.getBeer().getId()));
  }

}