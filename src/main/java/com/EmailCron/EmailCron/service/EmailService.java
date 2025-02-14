package com.EmailCron.EmailCron.service;

import com.EmailCron.EmailCron.config.SendGridConfigurationProperties;
import com.EmailCron.EmailCron.config.SendgridConfig;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    private final SendGrid sendGrid;
    private final SendGridConfigurationProperties properties;

    public EmailService(SendGrid sendGrid, SendGridConfigurationProperties properties) {
        this.sendGrid = sendGrid;
        this.properties = properties;
    }

    public void sendEmail(String subject, String content) {
        Email from = new Email(properties.getFromEmail(), properties.getFromName());
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(emailContent);

        String[] recipientArray = properties.getCorreos().split(",");
        for (String recipient : recipientArray) {
            Personalization personalization = new Personalization();
            personalization.addTo(new Email(recipient.trim()));
            mail.addPersonalization(personalization);
        }

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
