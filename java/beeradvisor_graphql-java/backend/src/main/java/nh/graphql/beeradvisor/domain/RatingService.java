package nh.graphql.beeradvisor.domain;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RatingService {

	private final UserRepository userRepository;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final BeerRepository beerRepository;

	@Autowired
	public RatingService(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher, BeerRepository beerRepository) {
		this.userRepository = userRepository;
		this.applicationEventPublisher = applicationEventPublisher;
		this.beerRepository = beerRepository;
	}

	@Transactional
	// EXAMPLE:
	// access fields from graphql fetchers in Spring Security: userId must match logged
	// in user
	// (checks makes no sense, just for demo!)
	@PreAuthorize("isAuthenticated() && #addRatingInput.userId == authentication.principal.id")
	public Rating addRating(AddRatingInput addRatingInput) {
		User user = userRepository.getUser(addRatingInput.getUserId());
		Beer beer = beerRepository.getBeer(addRatingInput.getBeerId());

		Rating rating = beer.addRating(user, addRatingInput.getComment(), addRatingInput.getStars());
		beerRepository.saveBeer(beer);

		applicationEventPublisher.publishEvent(new RatingCreatedEvent(rating));

		return rating;
	}
}
