package com.pavel.jbsrm.common.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailTemplate {

    String from;
    String to;
    String subject;
    String text;
}
