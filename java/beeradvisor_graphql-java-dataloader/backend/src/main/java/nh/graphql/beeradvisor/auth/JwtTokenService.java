package nh.graphql.beeradvisor.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class JwtTokenService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${jwt.expirationInMs}")
  private int jwtExpirationInMs;

  private final SecretKey secretKey;

  public JwtTokenService() {
      this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String createTokenForUser(User user) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return Jwts.builder()
        .setSubject(user.getId())
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(secretKey)
        .compact();
  }

  public String getUserIdFromToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
      return true;
    } catch (Exception ex) {
      logger.error("Invalid JWT token: " + ex, ex);
    }

    return false;
  }
}
