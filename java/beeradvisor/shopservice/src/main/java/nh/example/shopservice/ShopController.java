package nh.example.shopservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
@RequestMapping("/api/shops")
public class ShopController {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private ShopRepository shopRepository;

  @GetMapping
  @ResponseBody
  public List<Shop> shops(@RequestParam("withBeer") Optional<String> withBeer) {
    List<Shop> allShops = shopRepository.findAll();

    if (withBeer.isPresent()) {
      final String beerId = withBeer.get();
      logger.info("Finding Shops selling Beer {}", beerId);
      return allShops.stream().filter(s -> s.getBeers().contains(beerId)).collect(Collectors.toList());
    } else {
      logger.info("Returning all shopws");
    }

    return allShops;
  }

  @GetMapping("/{shopId}")
  @ResponseBody
  public Shop shop(@PathVariable("shopId") String shopId) {
    return this.shopRepository.findById(shopId).orElseThrow(() -> new ShopNotFoundException());
  }

}
