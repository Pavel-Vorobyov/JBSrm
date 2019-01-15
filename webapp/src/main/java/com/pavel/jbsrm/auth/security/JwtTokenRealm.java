package com.pavel.jbsrm.auth.security;

import com.pavel.jbsrm.auth.model.AuthUserDetails;
import com.pavel.jbsrm.auth.model.JwtCredentials;
import com.pavel.jbsrm.auth.model.JwtUser;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.common.utill.StringConverter;
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

    public Optional<AuthUserDetails> authenticate(JwtCredentials credentials) {
        return userRepository.findByEmail(credentials.getEmail())
                .map(user -> user.getPassword().equals(StringConverter.getHash(credentials.getPassword()))
                        ? new AuthUserDetails(user.getId(), tokenGenerator.getToken(ObjectMapperUtills.mapTo(user, JwtUser.class)), user.getUserRole())
                        : null );
    }
}
