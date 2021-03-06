package com.ers.application.email;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@RestController
@RequestMapping("emails")
public class EmailController {

    private final JavaMailSender mailSender;
    private final EmailHelper emailHelper;

    @Autowired
    public EmailController(JavaMailSender mailSender, EmailHelper emailHelper) {
        this.mailSender = mailSender;
        this.emailHelper = emailHelper;
    }

    /*
     * For now, this functionality is only available if application is hosted locally.
     */
    @PostMapping("registrations/event/{eventCode}/slot/{slotCode}/user/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String sendMail(@PathVariable String eventCode, @PathVariable String slotCode, @PathVariable int id) throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        emailHelper.prepareMessage(eventCode, slotCode, id, messageHelper);
        mailSender.send(message);
        return "Mail Sent Successfully!";
    }
}
