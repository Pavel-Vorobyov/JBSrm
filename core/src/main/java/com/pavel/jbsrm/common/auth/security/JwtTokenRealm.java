package com.pavel.jbsrm.common.auth.security;

import com.pavel.jbsrm.common.auth.model.JwtCredentials;
import com.pavel.jbsrm.common.auth.model.JwtUser;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenRealm {

    private UserRepository userRepository;
    private JwtTokenGenerator tokenGenerator;

    public JwtTokenRealm(UserRepository userRepository, JwtTokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public Optional<JwtUser> authenticate(JwtCredentials credentials) {
        return userRepository.findByEmailAndPassword(credentials.getEmail(), credentials.getPassword())
                .map(user -> {
                    JwtUser jwtUser = ObjectMapperUtills.mapTo(user, JwtUser.class);
                    jwtUser.setToken(tokenGenerator.getToken(jwtUser));
                    return jwtUser;
                });
    }
}
