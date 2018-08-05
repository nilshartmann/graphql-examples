package nh.graphql.beeradvisor.login;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import nh.graphql.beeradvisor.user.User;
import nh.graphql.beeradvisor.user.UserRepository;

/**
 * DumpAuthenticationFilter
 */
public class DumbAuthenticationFilter extends OncePerRequestFilter {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    logger.info(">>>>>>>> FILTER <<<<<<<<< ");
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      if (authHeader.startsWith("AUTH-")) {
        String userId = authHeader.substring("AUTH-".length());
        logger.info("USER ID ===> " + userId);
        User user = userRepository.getUser(userId);
        if (user == null) {
          throw new BadCredentialsException("Invalid User in Token");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

      } else {
        throw new BadCredentialsException("Invalid Auth Header");
      }
    }
    filterChain.doFilter(request, response);
  }

}