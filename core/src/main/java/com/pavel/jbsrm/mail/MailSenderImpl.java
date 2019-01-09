package com.pavel.jbsrm.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
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
