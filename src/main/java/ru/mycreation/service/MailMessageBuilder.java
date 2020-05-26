package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.mycreation.dto.UserDto;

@Service
public class MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildUserRegEmail(UserDto userDto) {
        Context context = new Context();
        context.setVariable("userDto", userDto);
        return templateEngine.process("registration-mail", context);
    }

    public String buildUserResetEmail(UserDto userDto) {
        Context context = new Context();
        context.setVariable("userDto", userDto);
        return templateEngine.process("reset_password_mail", context);
    }
}
