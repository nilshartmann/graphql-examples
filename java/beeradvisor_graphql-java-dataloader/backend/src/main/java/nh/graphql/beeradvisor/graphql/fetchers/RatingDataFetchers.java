package nh.graphql.beeradvisor.graphql.fetchers;

import graphql.schema.DataFetcher;
import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RatingDataFetchers {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    public RatingDataFetchers(UserService userService) {
        this.userService = userService;
    }

    public DataFetcher<User> authorFetcher() {
        return environment -> {
            Rating rating = environment.getSource();
            final String userId = rating.getUserId();

            logger.info("Reading author with id '{}'", userId);
            return userService.getUser(userId);
        };
    }
}
