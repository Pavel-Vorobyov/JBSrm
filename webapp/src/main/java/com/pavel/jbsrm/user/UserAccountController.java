package com.pavel.jbsrm.user;

import com.pavel.jbsrm.auth.model.JwtCredentials;
import com.pavel.jbsrm.auth.security.JwtTokenRealm;
import com.pavel.jbsrm.common.auth.UserDetails;
import com.pavel.jbsrm.user.repository.UserRepository;
import com.pavel.jbsrm.user.service.RegistrationLinkManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class UserAccountController {

    private UserRepository userRepository;
    private JwtTokenRealm jwtTokenRealm;
    private RegistrationLinkManager registrationLinkManager;

    public UserAccountController(UserRepository userRepository, JwtTokenRealm jwtTokenRealm, RegistrationLinkManager registrationLinkManager) {
        this.userRepository = userRepository;
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