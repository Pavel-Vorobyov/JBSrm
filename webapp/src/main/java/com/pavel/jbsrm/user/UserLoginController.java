package com.pavel.jbsrm.user;

import com.pavel.jbsrm.auth.model.JwtCredentials;
import com.pavel.jbsrm.auth.security.JwtTokenRealm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserLoginController {

    JwtTokenRealm jwtTokenRealm;

    public UserLoginController(JwtTokenRealm jwtTokenRealm) {
        this.jwtTokenRealm = jwtTokenRealm;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody JwtCredentials credentials) {
        return jwtTokenRealm.authenticate(credentials)
                .map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}