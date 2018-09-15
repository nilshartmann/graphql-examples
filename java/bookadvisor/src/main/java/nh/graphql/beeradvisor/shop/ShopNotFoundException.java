package nh.graphql.beeradvisor.shop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "shop not found")
public class ShopNotFoundException extends RuntimeException {
}
