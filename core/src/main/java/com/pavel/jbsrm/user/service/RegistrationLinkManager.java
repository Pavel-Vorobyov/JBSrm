package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.common.utill.StringConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class RegistrationLinkManager {

    @Value("${jbsrm.app.registration_secret}")
    private String registrationSecrete;

    @Value("${jbsrm.app.registration_receiver}")
    private String registrationReceiver;

    Optional<String> getLink(long id) {
        String key = StringConverter.getHash(id + registrationSecrete);
        return Optional.of(registrationReceiver + "?id=" + id + "&key=" + key);
    }

    public boolean verify(long id, String key) {
        return key.equals(StringConverter.getHash(id + registrationSecrete));
    }
}
