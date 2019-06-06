package nh.graphql.beeradvisor.graphql.fetchers;

import graphql.schema.DataFetcher;
import nh.graphql.beeradvisor.domain.*;
import nh.graphql.beeradvisor.graphql.subscription.RatingPublisher;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerAdvisorDataFetcher {
    private final Logger logger = LoggerFactory.getLogger(BeerAdvisorDataFetcher.class);

    private final BeerRepository beerRepository;
    private final BeerService beerService;
    private final ShopRepository shopRepository;
    private final RatingService ratingService;
    private final RatingPublisher ratingPublisher;

	public BeerAdvisorDataFetcher(BeerRepository beerRepository, BeerService beerService, ShopRepository shopRepository, RatingService ratingService, RatingPublisher ratingPublisher) {
		this.beerRepository = beerRepository;
		this.beerService = beerService;
		this.shopRepository = shopRepository;
		this.ratingService = ratingService;
		this.ratingPublisher = ratingPublisher;
	}

	public DataFetcher<Beer> beerFetcher() {
        return environment -> {
            String beerId = environment.getArgument("beerId");
            return beerRepository.getBeer(beerId);
        };
    }

    public DataFetcher<List<Beer>> beersFetcher() {
        return environment -> beerRepository.findAll();
    }

    public DataFetcher<Beer> updateBeerNameFetcher() {
    	return environment -> {
			String beerId = environment.getArgument("beerId");
			String newName = environment.getArgument("newName");

			return beerService.updateBeer(beerId, newName);
		};
	}

    public DataFetcher shopFetcher() {
        return environment -> {
            String shopId = environment.getArgument("shopId");
            return shopRepository.findShop(shopId);
        };
    }

    public DataFetcher shopsFetcher() {
        return environment -> shopRepository.findAll();
    }

    public DataFetcher<Rating> addRatingMutationFetcher() {
        return environment -> {
            // Input is always a Map, see: https://github.com/graphql-java/graphql-java/pull/782/files
            final Map<String, Object> ratingInput = environment.getArgument("ratingInput");
            AddRatingInput addRatingInput = new AddRatingInput();
            addRatingInput.setBeerId((String)ratingInput.get("beerId"));
            addRatingInput.setComment((String)ratingInput.get("comment"));
            addRatingInput.setStars((Integer)ratingInput.get("stars"));
            addRatingInput.setUserId((String)ratingInput.get("userId"));

            logger.debug("Rating Input {}", addRatingInput);
            return ratingService.addRating(addRatingInput);
        };
    }

    public DataFetcher<Publisher<Rating>> onNewRatingSubscriptionFetcher() {
        return environment -> this.ratingPublisher.getPublisher();
    }

    public DataFetcher<Publisher<Rating>> newRatingsSubscriptionFetcher() {
        return environment -> {
            String beerId = environment.getArgument("beerId");
            logger.info("Subscription for 'newRatings' (" + beerId + ") received");
            return this.ratingPublisher.getPublisher(beerId);
        };
    }

    public DataFetcher<String> pingFetcher() {
        return environment -> {
            String msg = environment.getArgument("msg");
            if (msg == null) {
                return "HELLO";
            }

            return "HELLO, " + msg;
        };
    }
}
