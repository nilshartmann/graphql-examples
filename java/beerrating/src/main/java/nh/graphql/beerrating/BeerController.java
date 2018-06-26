package nh.graphql.beerrating;

import nh.graphql.beerrating.model.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class BeerController {

  @Autowired
  private BeerRepository beerRepository;

  @GetMapping("/beers")
  @ResponseBody
  public Map<String, Object> beers() {
    Beer b = beerRepository.findAll().get(0);
    Map<String, Object> result = new Hashtable<>();
    result.put("name", b.getName());
    result.put("ratings", b.getRatings().size());

    return result;
  }
  @GetMapping("/beer")
  @ResponseBody
  public Map<String, Object>  beer() {
    Beer b = beerRepository.getBeer("B1");
    Map result = new Hashtable<String, String>();
    result.put("name", b.getName());
    result.put("ratings", b.getRatings().size());

    return result;
  }
}
