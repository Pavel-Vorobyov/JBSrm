package com.pavel.jbsrm.common.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class MailSenderImplTest implements MailSender{

    @Override
    public void sendMail(MailTemplate mailTemplate) {

    }
}
