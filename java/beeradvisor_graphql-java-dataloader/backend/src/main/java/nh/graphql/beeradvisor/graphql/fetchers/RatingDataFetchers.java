package nh.graphql.beeradvisor.graphql.fetchers;

import graphql.schema.DataFetcher;
import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Rating;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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

    public BatchLoader<String, User> userBatchLoader = new BatchLoader<String, User>() {
        @Override
        public CompletableFuture<List<User>> load(List<String> ids) {
            logger.info("Batch Loading Users with Ids '{}'", ids);
            return CompletableFuture.supplyAsync(() -> userService.findUsersWithId(ids));
        }
    };


    public DataFetcher authorFetcher() {
        return environment -> {
            Rating rating = environment.getSource();
            boolean skipDataLoader = environment.getField().getDirective("skipDataLoader") != null;
            final String userId = rating.getUserId();

            if (skipDataLoader) {
                logger.info("Reading user (author) with id '{}' WITHOUT DataLoader", userId);
                return userService.getUser(userId);
            }

            logger.info("Reading user (author) with id '{}' WITH DataLoader", userId);
            DataLoader<String, User> dataLoader = environment.getDataLoader("user");
            return dataLoader.load(userId);
        };
    }
}
