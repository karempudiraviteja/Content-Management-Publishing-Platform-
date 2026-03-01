package com.micro.security.jwt;

import com.micro.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;


@Service
public class JwtService {

    private final String secret = "your_very_secret_key_at_least_32_characters_long";

    private SecretKey getSigningKey() {
    	return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // --- EXISTING METHODS ---
 // Update this method in your JwtService.java
    public String generateToken(User user) {
        // 1. Create a map for custom claims
        java.util.Map<String, Object> claims = new java.util.HashMap<>();
        
        // 2. Add the role to the claims (make sure the key matches what React expects)
        claims.put("role", user.getRole().name()); 

        return Jwts.builder()
                .setClaims(claims)           // Put the role here
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }
	
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
			
	}

    // --- ADDING MISSING METHODS TO FIX YOUR ERROR ---

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}