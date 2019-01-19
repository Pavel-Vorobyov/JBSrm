package com.pavel.jbsrm.user;

import com.pavel.jbsrm.auth.model.JwtCredentials;
import com.pavel.jbsrm.auth.security.JwtProvider;
import com.pavel.jbsrm.user.service.RegistrationLinkManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class UserAccountController {

    private JwtProvider jwtProvider;
    private RegistrationLinkManager registrationLinkManager;
    private AuthenticationManager authenticationManager;

    public UserAccountController(JwtProvider jwtProvider, RegistrationLinkManager registrationLinkManager, AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.registrationLinkManager = registrationLinkManager;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtCredentials credentials) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(jwtProvider.generateJwtToken(authentication));
    }

    @PostMapping("/register")
    public boolean register(long id, String key) {
        return registrationLinkManager.verify(id, key);
    }
}