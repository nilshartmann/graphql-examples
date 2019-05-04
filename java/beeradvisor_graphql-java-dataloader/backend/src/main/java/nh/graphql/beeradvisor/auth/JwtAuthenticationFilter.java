package nh.graphql.beeradvisor.auth;

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
import java.util.Collections;
import java.util.List;

/**
 * DumpAuthenticationFilter
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final List<SimpleGrantedAuthority> ROLE_USER = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
/*
    logger.info(">>>>>>>> FILTER <<<<<<<<< ");
    StringBuilder builder = new StringBuilder("HEADER:\n");
    final Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = request.getHeader(headerName);
      builder.append(String.format("'%s' => '%s'%n", headerName, headerValue));
    }

    builder.append(String.format("requestUri => '%s'%n", request.getRequestURI()));

    logger.info(builder.toString());
*/

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
    if (token != null) {
      if (!jwtTokenService.validateToken(token)) {
        throw new BadCredentialsException("Invalid authorization token (maybe expired?)");
      }

      String userId = jwtTokenService.getUserIdFromToken(token);
      User user = userService.getUser(userId);
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