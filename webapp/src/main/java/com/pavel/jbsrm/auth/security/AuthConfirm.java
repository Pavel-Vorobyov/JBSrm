package com.pavel.jbsrm.auth.security;

import com.pavel.jbsrm.common.utill.StringConverter;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthConfirm {

    @Value("${jbsrm.app.registration_secret}")
    private String registrationSecrete;

    private UserRepository userRepository;

    public AuthConfirm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean confirm(long id, String key) {
        Optional<User> user = userRepository.findById(id);
        String keyHash = StringConverter.getHash(key);
        String actualKey = StringConverter.getHash(id + registrationSecrete);

        return user.isPresent() && keyHash.equals(actualKey);
    }
}
