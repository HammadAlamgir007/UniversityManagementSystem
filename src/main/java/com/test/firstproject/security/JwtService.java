package com.test.firstproject.security;
import io.jsonwebtoken.Jwts;

import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public Date extractExpiration(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
    public String generateToken(String username) {

        return Jwts.builder()

                .subject(username)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + accessExpiration
                        )
                )

                .signWith(getSigningKey())

                .compact();

    }
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)

                .before(new Date());

    }
    public boolean isTokenValid(
            String token,
            String username
    ) {

        return extractUsername(token).equals(username)
                && !isTokenExpired(token);

    }
}

