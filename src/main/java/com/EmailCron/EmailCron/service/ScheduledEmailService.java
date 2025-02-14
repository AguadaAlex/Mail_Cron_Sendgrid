package com.EmailCron.EmailCron.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledEmailService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledEmailService.class);
    private final EmailService emailService;

    public ScheduledEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void sendScheduledEmails() {
        logger.info("Inicializando el envío de correos programados");
        String subject = " esto es una prueba utilizando SendGrid";
        String content = "Hola a todos,\n\nEste es un correo automatizado enviado a varios destinatarios utilizando la API de SendGrid en Java Spring Boot.\n\n¡Saludos!";

        emailService.sendEmail(subject, content);
        logger.info("Finalizando el envío de correos programados");
    }
}
