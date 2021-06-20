package com.remodelingllc.api.service;

import com.remodelingllc.api.dto.EmailDTO;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailContentService {

    private final TemplateEngine templateEngine;

    private final String CONTACT_EMAIL_TEMPLATE = "ContactEmail";
    private final String PASSWORD_RECOVERY_EMAIL_TEMPLATE = "PasswordRecoveryEmail";

    public EmailContentService(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildContactEmail(final EmailDTO email) {
        Context context = new Context();
        context.setVariable("message", email.getMessage());
        context.setVariable("phoneNumber", email.getPhoneNumber());
        context.setVariable("from", email.getFrom());
        context.setVariable("subject", email.getSubject());
        return templateEngine.process(CONTACT_EMAIL_TEMPLATE, context);
    }

    public String buildPasswordRecoveryEmail(final EmailDTO email) {
        Context context = new Context();
        context.setVariable("username", email.getUsername());
        context.setVariable("token", email.getToken());
        return templateEngine.process(PASSWORD_RECOVERY_EMAIL_TEMPLATE, context);
    }

}
