package nh.graphql.beeradvisor.auth;

import nh.graphql.beeradvisor.Utils;
import nh.graphql.beeradvisor.domain.Shop;
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
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final String userServiceUrl;
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate, @Value("${beeradvisor.userservice.url}") String url) {
        this.userServiceUrl = url;
        this.restTemplate = restTemplate;
    }

    public User getUser(String userId) {
        List<String> userIds = Utils.listOf(userId);

        List<User> users = findUsersWithId(userIds);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public List<User> findUsersWithId(List<String> userIds) {

        logger.info("requesting users with Ids '{}' from '{}'", userIds, userServiceUrl);

        ResponseEntity<List<User>> response = restTemplate.exchange(this.userServiceUrl + "/users/{userIds}", HttpMethod.GET,
            null, new ParameterizedTypeReference<List<User>>() {
            }, String.join(",", userIds));
        List<User> users = response.getBody();
        return users;
    }

    public User getUserByLogin(String userName) {
        logger.info("login with userName '{}' from '{}'", userName, userServiceUrl);
        User user = restTemplate.getForObject(this.userServiceUrl + "/login/{userName}", User.class, userName);
        return user;
    }
}
