package nh.graphql.beeradvisor.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class ShopService {

	private final RestTemplate restTemplate;
	private final String shopApiUrl;

	public ShopService(RestTemplate restTemplate, @Value("${beeradivsor.shop.apiUrl}") String shopApiUrl) {
		this.restTemplate = restTemplate;
		this.shopApiUrl = shopApiUrl;
	}

	public Shop findShop(String shopId) {
		Shop shop = restTemplate.getForObject(
				this.shopApiUrl + "/{shopId}", Shop.class, shopId);

		return shop;
	}

	public List<Shop> getShops() {
		ResponseEntity<List<Shop>> response = restTemplate.exchange(
				this.shopApiUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Shop>>() {
				});
		List<Shop> shops = response.getBody();
		return shops;
	}

	public List<Shop> findShopsForBeer(String beerId) {
		ResponseEntity<List<Shop>> response = restTemplate.exchange(
				this.shopApiUrl + "?beerId={beerId}",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Shop>>() {
				},
				beerId
		);
		List<Shop> shops = response.getBody();
		return shops;
	}
}
