package nh.graphql.beeradvisor.login;

import nh.graphql.beeradvisor.auth.JwtTokenService;
import nh.graphql.beeradvisor.user.User;
import nh.graphql.beeradvisor.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * LoginController
 */
@Controller
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenService jwtTokenService;

  @PostMapping("/api/login")
  @Valid
  @ResponseBody
  public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    logger.info("Login {}", loginRequest);

    User user = userRepository.getUser(loginRequest.getUserId());
    if (user == null) {
      throw new BadCredentialsException("Unknown UserId");
    }

    final String token = jwtTokenService.createTokenForUser(user);

    return new LoginResponse(user.getName(), token);
  }

}