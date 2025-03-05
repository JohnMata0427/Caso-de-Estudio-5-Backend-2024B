package com.backend.conferencias.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secretKey;

  public String generateToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4)) // 4 horas
        .signWith(getSigningKey())
        .compact();
  }

  public boolean validateToken(String token, String username) {
    final String tokenUsername = extractUsername(token);
    return (tokenUsername.equals(username) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parser()
          .verifyWith(getSigningKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (Exception e) {
      throw new RuntimeException("Error al procesar el token JWT", e);
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}