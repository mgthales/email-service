package com.monteiro.email_service.infra.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.monteiro.email_service.adpters.EmailSenderGateway;
import com.monteiro.email_service.core.exception.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService){
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        // Criando o conteúdo do e-mail
        Content subjectContent = new Content().withData(subject);
        Content bodyContent = new Content().withData(body);

        Body emailBody = new Body().withText(bodyContent);
        Message message = new Message().withSubject(subjectContent).withBody(emailBody);

        // Configuração do e-mail
        SendEmailRequest request = new SendEmailRequest()
                .withSource("thalesmg3@gmail.com") // Remetente
                .withDestination(new Destination().withToAddresses(to)) // Destinatário
                .withMessage(message); // Mensagem do e-mail

        try {
            this.amazonSimpleEmailService.sendEmail(request); // Envio do e-mail
        } catch (AmazonServiceException exception) {
            throw new EmailServiceException("Failure while sending email", exception); // Erro no envio
        }
    }
}
