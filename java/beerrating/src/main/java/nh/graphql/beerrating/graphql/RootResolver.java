package nh.graphql.beerrating.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nh.graphql.beerrating.BeerRepository;
import nh.graphql.beerrating.model.Beer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RootResolver implements GraphQLQueryResolver {

  private static Logger logger = LoggerFactory.getLogger(RootResolver.class);

  @Autowired
  private BeerRepository beerRepository;

  public List<Beer> beers() {
//    logger.info("o1" + o1);
    //  logger.info("o2" + o2);
    //return beerRepository.findAllFetchGraph(true);
    return beerRepository.findAll();
  }

}
