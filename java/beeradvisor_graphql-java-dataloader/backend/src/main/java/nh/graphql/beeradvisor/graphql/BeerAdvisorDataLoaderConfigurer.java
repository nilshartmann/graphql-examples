package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.graphql.fetchers.RatingDataFetchers;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerAdvisorDataLoaderConfigurer {

    private final RatingDataFetchers ratingDataFetchers;

    public BeerAdvisorDataLoaderConfigurer(RatingDataFetchers ratingDataFetchers) {
        this.ratingDataFetchers = ratingDataFetchers;
    }

    public DataLoaderRegistry configureDataLoader(Optional<DataLoaderRegistry> dataLoaderRegistryCandidate) {
        final DataLoaderRegistry dataLoaderRegistry = dataLoaderRegistryCandidate.orElse(new DataLoaderRegistry());
        DataLoader dl = DataLoader.newDataLoader(ratingDataFetchers.userBatchLoader);
        dataLoaderRegistry.register("user", dl);

        return dataLoaderRegistry;
    }

}
