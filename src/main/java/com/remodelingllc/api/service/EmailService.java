package com.remodelingllc.api.service;

import com.remodelingllc.api.dto.EmailDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EmailService {

    @Value("${com.remodelingllc.corporated.email}")
    private String corporatedEmail;

    private final String EMAIL_ENCODING = "utf-8";

    private final JavaMailSender mailSender;
    private final EmailContentService emailContentService;

    public EmailService(final JavaMailSender mailSender,
                        final EmailContentService emailContentService) {
        this.mailSender = mailSender;
        this.emailContentService = emailContentService;
    }

    public boolean sendContactNotificationEmail(final EmailDTO email) {
        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, EMAIL_ENCODING);
                messageHelper.setFrom(email.getFrom());
                messageHelper.setTo(corporatedEmail);
                messageHelper.setSubject(email.getSubject());
                String body = emailContentService.buildContactEmail(email);
                messageHelper.setText(body, true);
            };
            mailSender.send(messagePreparator);
            return true;
        } catch (Exception e) {
            log.error("Error sending contact email");
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean sendPasswordRecoveryEmail(final EmailDTO email) {
        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, EMAIL_ENCODING);
                messageHelper.setFrom(corporatedEmail);
                messageHelper.setTo(email.getTo());
                messageHelper.setSubject(email.getSubject());
                String body = emailContentService.buildPasswordRecoveryEmail(email);
                messageHelper.setText(body, true);
            };
            mailSender.send(messagePreparator);
            return true;
        } catch (Exception e) {
            log.error("Error sending password recovery email");
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

}
