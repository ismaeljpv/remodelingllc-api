package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.EmailDTO;
import com.remodelingllc.api.dto.GenericMessageDTO;
import com.remodelingllc.api.service.EmailService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailResource {

    private EmailService emailService;

    public EmailResource(final EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/contact/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericMessageDTO sendContactNotificationEmail(@Validated @RequestBody final EmailDTO email) {
        boolean success = emailService.sendContactNotificationEmail(email);
        String message = (success) ? "Email sended successfully!, we will contact you very soon."
                : "Oops ! There was an error sendind the email, please try again later.";
        return new GenericMessageDTO(message, success);
    }

}
