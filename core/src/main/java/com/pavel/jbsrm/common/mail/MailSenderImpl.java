package com.pavel.jbsrm.common.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "prod"})
public class MailSenderImpl implements MailSender {

    private JavaMailSender mailSender;

    public MailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(MailTemplate mail) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mail.to);
        message.setSubject(mail.subject);
        message.setText(mail.text);

        mailSender.send(message);
    }
}
