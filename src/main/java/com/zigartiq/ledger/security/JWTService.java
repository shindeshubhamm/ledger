package com.zigartiq.ledger.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;

@Service
public class JWTService {

    private String secret;
    private long expiration;

    private JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            secret = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Something went wrong!", e);
        }
    }

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
