package com.pavel.jbsrm.user;

import com.pavel.jbsrm.auth.model.JwtCredentials;
import com.pavel.jbsrm.auth.security.JwtTokenRealm;
import com.pavel.jbsrm.user.service.RegistrationLinkManager;
import com.pavel.jbsrm.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class UserAccountController {

    private JwtTokenRealm jwtTokenRealm;
    private RegistrationLinkManager registrationLinkManager;

    public UserAccountController(JwtTokenRealm jwtTokenRealm, RegistrationLinkManager registrationLinkManager) {
        this.jwtTokenRealm = jwtTokenRealm;
        this.registrationLinkManager = registrationLinkManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtCredentials credentials) {
        return jwtTokenRealm.authenticate(credentials)
                .map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("/register")
    public boolean register(long id, String key) {
        return registrationLinkManager.verify(id, key);
    }
}