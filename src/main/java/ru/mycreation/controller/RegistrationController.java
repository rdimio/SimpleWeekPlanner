package ru.mycreation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mycreation.dto.UserDto;
import ru.mycreation.entities.VerificationToken;
import ru.mycreation.service.TokenService;
import ru.mycreation.service.MailService;
import ru.mycreation.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class RegistrationController {

    private UserService userService;
    private MailService mailService;
    private TokenService tokenService;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setMailService(MailService mailService) { this.mailService = mailService;}

    @Autowired
    public void setTokenService(TokenService tokenService) { this.tokenService = tokenService;}

    @GetMapping("/registration")
    public String registration(Model model){
/*        List<VerificationToken> tokens = tokenService.findAll();
        if(tokens!=null) {
            Date dateNow = new Date();
            for (VerificationToken vt : tokens) {
                if (vt.calculateExpiryDate().compareTo(dateNow) >= 0)
                    tokenService.delete(vt);
            }
        }*/
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute(name = "userDto") UserDto userDto,
                                      BindingResult bindingResult, Model model){
        if(userService.findByLogin(userDto.getLogin())!=null) {
            bindingResult.rejectValue("login","this login is already exists");
            model.addAttribute(userDto);
            return "registration";
        }
        if(userService.findByEmail(userDto.getEmail())!=null) {
            bindingResult.rejectValue("email","this email is already registered");
            model.addAttribute(userDto);
            return "registration";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password and confirmPassword are not equal");
            model.addAttribute(userDto);
            return "registration";
        }
        String token = generateNewToken();
        while(tokenService.findOneByToken(token)!=null){
            token = generateNewToken();
        }
        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setLogin(userDto.getLogin());
        vt.setPassword(userDto.getPassword());
        vt.setEmail(userDto.getEmail());
        vt.setExpiryDate(new Date());
        tokenService.save(vt);
        userDto.setToken(token);
        mailService.sendConfRegMail(userDto);
        model.addAttribute("token" , token);
        return "confirm_registration";
    }


    @GetMapping("/registration/confirm/{token}")
    public String confirmRegistration(@PathVariable String token){
        UserDto userDto = new UserDto();
        if(tokenService.findOneByToken(token)!=null) {
            VerificationToken vt = tokenService.findOneByToken(token);
            userDto.setLogin(vt.getLogin());
            userDto.setPassword(vt.getPassword());
            userDto.setEmail(vt.getEmail());
            userService.registrationUser(userDto);
            tokenService.delete(vt);
            return "plan";
        }
        else return "confirm_registration";
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
