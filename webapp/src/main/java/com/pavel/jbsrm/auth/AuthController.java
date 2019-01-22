package com.pavel.jbsrm.auth;

import com.pavel.jbsrm.auth.model.AuthUser;
import com.pavel.jbsrm.auth.model.JwtCredentials;
import com.pavel.jbsrm.auth.security.AuthConfirm;
import com.pavel.jbsrm.auth.security.JwtProvider;
import com.pavel.jbsrm.common.auth.UserPrinciple;
import com.pavel.jbsrm.user.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private AuthConfirm authConfirm;

    public AuthController(JwtProvider jwtProvider, AuthenticationManager authenticationManager, AuthConfirm authConfirm) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.authConfirm = authConfirm;
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
        String token = jwtProvider.generateJwtToken(authentication);
        UserRole userRole = ((UserPrinciple) authentication.getPrincipal()).getUserRole();

        return ResponseEntity.ok(AuthUser.builder()
                .token(token)
                .tokenType("Bearer")
                .userRole(userRole)
                .build());
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(long id, String key) {
        return authConfirm.confirm(id, key)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
