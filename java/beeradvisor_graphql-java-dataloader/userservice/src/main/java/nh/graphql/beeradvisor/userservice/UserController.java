package nh.graphql.beeradvisor.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users/{userIds}")
    public List<User> users(@PathVariable String[] userIds) {

        logger.info("Finding users with ids '{}'", Arrays.asList(userIds));

        return userRepository.findUsersWithId(userIds);
    }

    @GetMapping(value = "/login/{login}")
    public User login(@PathVariable String login) {

        logger.info("Finding users with login '{}'", login);

        return userRepository.findUserWithLogin(login);
    }

}
