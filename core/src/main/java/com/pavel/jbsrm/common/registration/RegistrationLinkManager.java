package com.pavel.jbsrm.common.registration;

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

    public Optional<String> getLink(long id) {
        String key = "UNDEFINED";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((id + registrationSecrete).getBytes());

            key = Arrays.toString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return Optional.of(registrationReceiver + "?id=" + id + "&key=" + key);
    }

    public boolean verify(long id, String key) {
        boolean flag = false;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((id + registrationSecrete).getBytes());

            flag = MessageDigest.isEqual(messageDigest.digest(), key.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return flag;
    }
}
