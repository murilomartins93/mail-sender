package org.jfm.application;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jfm.domain.EmailRequest;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    public void sendEmail(String email, String messageContent) {
        mailer.send(Mail.withText(
            email,
            "FiapX Video Notification",
            messageContent
        ));
    }
}