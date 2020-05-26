package ru.mycreation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mycreation.dto.UserDto;
import ru.mycreation.entities.User;
import ru.mycreation.service.MailService;
import ru.mycreation.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class RegistrationController {

    private UserService userService;
    private MailService mailService;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setMailService(MailService mailService) { this.mailService = mailService;}

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute(name = "userDto") UserDto userDto, Model model){
        if(userService.findByLogin(userDto.getLogin())!=null) {
            model.addAttribute("findByLogin_error","user.exists");
            return "registration";
        }
        if(userService.findByEmail(userDto.getEmail())!=null) {
            model.addAttribute("findByEmail_error","user.email.exists");
            return "registration";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "passwords.no.match");
            return "registration";
        }
        String token = generateNewToken();
        userDto.setToken(token);
        userService.registrationUser(userDto);
        mailService.sendConfRegMail(userDto);
        model.addAttribute("flag", 1);
        return "confirm_registration";
    }

    @GetMapping("/registration/confirm/{token}")
    public String confReg(@PathVariable String token) {
        User user = userService.findOneByToken(token);
        if(user!=null){
            user.setToken(null);
            user.setEnabled(false);
            userService.save(user);
            return "redirect:/login";
        }
        else return "redirect:/registration";
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @GetMapping("/reset/password")
    public String resetPassGet(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("reset",1);
        model.addAttribute("userDto", userDto);
        return "reset_password";
    }

    @PostMapping("/reset/password")
    public String resetPassPost(@ModelAttribute(name = "userDto") UserDto userDto, Model model){
        if(userDto.getPassword() == null) {

            if (userService.findByLogin(userDto.getLogin()) == null &&
                    userService.findByEmail(userDto.getEmail()) == null) {
                model.addAttribute("findByLoginAndEmail_error", "user.doesnt_exists");
                return "reset_password";
            } else {
                User user = userService.findByLogin(userDto.getLogin());
                if (user == null)
                    user = userService.findByEmail(userDto.getEmail());
                String token = generateNewToken();
                user.setToken(token);
                userDto.setToken(token);
                user.setEnabled(true);
                userDto.setEmail(user.getEmail());
                userService.save(user);
                mailService.sendResetPasMail(userDto);
                model.addAttribute("flag", 1);
                return "redirect:/";
            }

        } else {
            if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                model.addAttribute("passwordError", "passwords.no.match");
                return "reset_password";
            }
            User user = userService.findByLogin(userDto.getLogin());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.save(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/reset/password/{token}")
    public String confReset(@PathVariable String token, Model model) {
        User user = userService.findOneByToken(token);
        if(user!=null){
            user.setToken(null);
            user.setEnabled(false);
            userService.save(user);
            String login = user.getLogin();
            model.addAttribute("reset",null);
            model.addAttribute("login", login);
            return "reset_password";
        }
        else return "redirect:/login";
    }
}
