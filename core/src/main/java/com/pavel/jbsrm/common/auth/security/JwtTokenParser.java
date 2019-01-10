package com.pavel.jbsrm.common.auth.security;

import com.pavel.jbsrm.common.auth.model.JwtUser;
import com.pavel.jbsrm.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenParser {

    @Value("${jbsrm.app.jwtSecret}")
    private String jwtSecret;

    public Optional<JwtUser> validate(String token) {
        Optional<JwtUser> jwtUser = Optional.empty();

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = Optional.of(JwtUser.builder()
                    .name(body.getSubject())
                    .id(Long.parseLong((String) body.get("userId")))
                    .userRole(UserRole.valueOf((String) body.get("role")))
                    .build());
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }
}