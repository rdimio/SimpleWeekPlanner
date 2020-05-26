package ru.mycreation.service;

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
            sendMail(userDto.getEmail(), String.format("confirm the registration"), messageBuilder.buildUserRegEmail(userDto));
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to create order mail message for user: " + userDto.getToken());
        } catch (MailException e) {
            throw new RuntimeException("Unable to send order mail message for user: " + userDto.getToken());
        }
    }

    public void sendResetPasMail(UserDto userDto) {
        try {
            sendMail(userDto.getEmail(), String.format("reset password"), messageBuilder.buildUserResetEmail(userDto));
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to create order mail message for user: " + userDto.getToken());
        } catch (MailException e) {
            throw new RuntimeException("Unable to send order mail message for user: " + userDto.getToken());
        }
    }
}