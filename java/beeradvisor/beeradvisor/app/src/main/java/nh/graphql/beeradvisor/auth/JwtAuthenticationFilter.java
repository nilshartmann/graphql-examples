package nh.graphql.beeradvisor.auth;

import nh.graphql.beeradvisor.user.User;
import nh.graphql.beeradvisor.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * DumpAuthenticationFilter
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  public static final List<SimpleGrantedAuthority> ROLE_USER = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    logger.info(">>>>>>>> FILTER <<<<<<<<< ");
    try {
      authenticateIfNeeded(request);
    } catch (AuthenticationException bed) {
      logger.error("Could not authenticate: " + bed, bed);
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private void authenticateIfNeeded(HttpServletRequest request) {
    final String token = getJwtFromRequest(request);
    logger.info(">>>>>>>> token available '{}' <<<<<<<<< ", token != null);
    if (token != null) {
      if (!jwtTokenService.validateToken(token)) {
        throw new BadCredentialsException("Invalid authorization token");
      }

      String userId = jwtTokenService.getUserIdFromToken(token);
      logger.info("USER ID ===> " + userId);
      User user = userRepository.getUser(userId);
      if (user == null) {
        throw new BadCredentialsException("Invalid User in Token");
      }

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
          ROLE_USER);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) {
      return null;
    }
    if (!authHeader.startsWith("Bearer ")) {
      throw new BadCredentialsException(
          "Invalid 'Authorization'-Header ('" + authHeader + "'). Expected format: 'Authorization: Bearer TOKEN'");
    }
    return authHeader.substring(7, authHeader.length());
  }

}