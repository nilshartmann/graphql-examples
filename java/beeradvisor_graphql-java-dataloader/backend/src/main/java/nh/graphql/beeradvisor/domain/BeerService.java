package nh.graphql.beeradvisor.domain;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class BeerService {

	private final BeerRepository beerRepository;

	public BeerService(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@PreAuthorize("isAuthenticated() && authentication.principal.id == 'U5'")
	public Beer updateBeer(String beerId, String newName) {
		final Beer beer = beerRepository.getBeer(beerId);
		beer.setName(newName);

		beerRepository.saveBeer(beer);
		return beer;
	}
}
