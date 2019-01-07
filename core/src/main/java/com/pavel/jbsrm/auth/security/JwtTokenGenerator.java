package com.pavel.jbsrm.auth.security;

import com.pavel.jbsrm.auth.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator {

    @Value("${jbsrm.app.jwtSecret}")
    private String jwtSecret;

    public String getToken(JwtUser jwtUser) {

        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getName());
        claims.put("userId", String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
