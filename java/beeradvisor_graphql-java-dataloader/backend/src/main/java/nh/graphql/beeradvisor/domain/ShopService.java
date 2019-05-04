package nh.graphql.beeradvisor.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

  private final RestTemplate restTemplate;
  private final String shopApiUrl;

  public ShopService(RestTemplate restTemplate, @Value("${beeradivsor.shop.apiUrl}") String shopApiUrl) {
    this.restTemplate = restTemplate;
    this.shopApiUrl = shopApiUrl;

    logger.info("USING SHOP API URL {}", this.shopApiUrl);

  }

  public Shop findShop(String shopId) {
    Shop shop = restTemplate.getForObject(this.shopApiUrl + "/{shopId}", Shop.class, shopId);

    return shop;
  }

  public List<Shop> getShops() {
    ResponseEntity<List<Shop>> response = restTemplate.exchange(this.shopApiUrl, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Shop>>() {
        });
    List<Shop> shops = response.getBody();
    return shops;
  }

  public List<Shop> findShopsForBeer(List<String> beerIds) {
    ResponseEntity<List<Shop>> response = restTemplate.exchange(this.shopApiUrl + "?withBeer={beerId}", HttpMethod.GET,
        null, new ParameterizedTypeReference<List<Shop>>() {
        }, String.join(",", beerIds));
    List<Shop> shops = response.getBody();
    return shops;
  }
}
