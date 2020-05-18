package ru.mycreation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.entities.Days;
import ru.mycreation.entities.User;
import ru.mycreation.service.DayService;
import ru.mycreation.service.TargetService;
import ru.mycreation.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class PlannerController {

    private TargetService targetService;
    private DayService dayService;
    private UserService userService;

    @Autowired
    public void setTargetService(TargetService targetService){
        this.targetService = targetService;
    }

    @Autowired
    public void setDayService(DayService dayService){
        this.dayService = dayService;
    }

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        String name = principal.getName();
        User user = userService.findByLogin(name);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/")
    public String index(Model model){
        int lang_flag;
        if(LocaleContextHolder.getLocale().equals(new Locale("ru"))){
            lang_flag = 1;
        } else lang_flag = 0;
        model.addAttribute("lang_flag", lang_flag);
        return "index";
    }

    @GetMapping("/plan")
    public String getPlan(Model model, Principal principal){
        String name = principal.getName();
        User user = userService.findByLogin(name);
        Set<String> targets = targetService.findDistinctTitle(user);
        List<Days> days;
        if(LocaleContextHolder.getLocale().equals(new Locale("ru"))){
            days = dayService.findAllRus();
        } else days = dayService.findAllEn();
        model.addAttribute("days", days);
        model.addAttribute("user", user);
        model.addAttribute("targets", targets);
        return "plan";
    }

    @GetMapping("/targets")
    public String targets(Model model, @RequestParam (required = false) Long id, Principal principal){
        List<Days> days;
        if(LocaleContextHolder.getLocale().equals(new Locale("ru"))){
            days = dayService.findAllRus();
        } else days = dayService.findAllEn();
        DayTargets target = new DayTargets();
        String name = principal.getName();
        User user = userService.findByLogin(name);
        if(id != null) {
            target = targetService.findById(id);
        } else {
            target.setUser(user);
        }
        model.addAttribute("user", user);
        model.addAttribute("target", target);
        model.addAttribute("days", days);
        return "targets_page";
    }

    @PostMapping("/targets")
    public String addTarget(@ModelAttribute(name = "target") @Valid DayTargets target,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Days> days;
            if(LocaleContextHolder.getLocale().equals(new Locale("ru"))){
                days = dayService.findAllRus();
            } else days = dayService.findAllEn();
            model.addAttribute("days", days);
            return "targets_page";
        } else {
            targetService.save(target);
        }
        return "targets_page";
    }

    @GetMapping("/targets/delete/{id}")
    public String deleteTarget(@PathVariable Long id){
        targetService.delete(id);
        return "redirect:/plan";
    }

}
