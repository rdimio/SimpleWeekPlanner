package ru.mycreation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.mycreation.dto.UserDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private JavaMailSender sender;
    private MailMessageBuilder messageBuilder;

    private Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    private void sendMail(String email, String subject, String text) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setText(text, true);
        helper.setSubject(subject);
        sender.send(message);
    }

    public void sendConfRegMail(UserDto userDto) {
        try {
            sendMail(userDto.getEmail(), String.format("confirm the registration"), messageBuilder.buildUserEmail(userDto));
        } catch (MessagingException e) {
            logger.warn("Unable to create order mail message for order: " + userDto.getToken());
        } catch (MailException e) {
            logger.warn("Unable to send order mail message for order: " + userDto.getToken());
        }
    }
}