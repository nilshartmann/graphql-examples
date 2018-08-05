package nh.graphql.beeradvisor.login;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import nh.graphql.beeradvisor.user.User;
import nh.graphql.beeradvisor.user.UserRepository;

/**
 * LoginController
 */
@Controller
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/api/login")
  @ResponseBody
  public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    logger.info("Login {}", loginRequest);

    User user = userRepository.getUser(loginRequest.getUserId());
    if (user == null) {
      throw new IllegalStateException("Unknown UserId");
    }

    return new LoginResponse(user, "AUTH-" + user.getId());
  }

}