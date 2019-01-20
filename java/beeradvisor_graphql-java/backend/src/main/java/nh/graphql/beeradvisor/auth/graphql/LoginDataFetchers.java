package nh.graphql.beeradvisor.auth.graphql;

import graphql.schema.DataFetcher;
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
public class LoginDataFetchers {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    public LoginDataFetchers(UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    public DataFetcher getLoginDataFetcher() {
        return environment -> {
            final String userName = environment.getArgument("username");
            logger.info("Login {}", userName);

            User user = userRepository.getUserByLogin(userName);
            if (user == null) {
                return LoginResponse.failed("Unknown userName");
            }

            final String token = jwtTokenService.createTokenForUser(user);

            return LoginResponse.succeeded(new Authentication(user.getId(), user.getName(), token));
        };
    }
}
