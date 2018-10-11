package nh.graphql.beeradvisor.auth.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import nh.graphql.beeradvisor.auth.JwtTokenService;
import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class LoginResolver implements GraphQLMutationResolver {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final UserRepository userRepository;
  private final JwtTokenService jwtTokenService;

  public LoginResolver(UserRepository userRepository, JwtTokenService jwtTokenService) {
    this.userRepository = userRepository;
    this.jwtTokenService = jwtTokenService;
  }

  public LoginResponse login(String userName) {
    logger.info("Login {}", userName);

    User user = userRepository.getUserByLogin(userName);
    if (user == null) {
      return LoginResponse.failed("Unknown userName");
    }

    final String token = jwtTokenService.createTokenForUser(user);

    return LoginResponse.succeeded(new Authentication(user.getId(), user.getName(), token));
  }
}
