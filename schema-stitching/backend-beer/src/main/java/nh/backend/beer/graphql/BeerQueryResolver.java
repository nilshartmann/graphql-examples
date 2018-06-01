package nh.backend.beer.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nh.backend.beer.model.Beer;
import nh.backend.beer.model.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerQueryResolver implements GraphQLQueryResolver {

  private final long bootTime;
  private final BeerRepository beerRepository;
  private final String serverPort;

  @Autowired
  public BeerQueryResolver(BeerRepository beerRepository, @Value("${server.port}") String serverPort) {
    this.beerRepository = beerRepository;
    this.serverPort = serverPort;

    bootTime = System.currentTimeMillis();
  }

  public List<Beer> beers() {
    return beerRepository.findAll();
  }

  public ProcessInfo ping() {
    return new ProcessInfo("🍻 The Beer Backend",
        String.format("%ds", (System.currentTimeMillis() - this.bootTime) / 1000), System.getProperty("java.version"),
        String.format("http://localhost:%s/graphiql", serverPort));
  }
}
