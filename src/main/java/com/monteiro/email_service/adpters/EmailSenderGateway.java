package com.monteiro.email_service.adpters;

import org.springframework.stereotype.Component;

@Component
public interface EmailSenderGateway {

    void sendEmail(String to, String subject, String body);
}
