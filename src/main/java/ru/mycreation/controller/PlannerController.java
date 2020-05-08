package ru.mycreation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mycreation.entities.DayTargets;
import ru.mycreation.entities.Days;
import ru.mycreation.entities.WeekTargets;
import ru.mycreation.service.DayService;
import ru.mycreation.service.TargetService;
import ru.mycreation.service.WeekService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PlannerController {

    private TargetService targetService;
    private DayService dayService;
    private WeekService weekService;

    @Autowired
    public void setTargetService(TargetService targetService){
        this.targetService = targetService;
    }

    @Autowired
    public void setTargetService(DayService dayService){
        this.dayService = dayService;
    }

    @Autowired
    public void setWeekService(WeekService weekService){
        this.weekService = weekService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<Days> days = dayService.findAll();
        List<WeekTargets> week = weekService.findAll();
        model.addAttribute("days", days);
        model.addAttribute("week", week);
        return "index";
    }

    @GetMapping("/targets")
    public String targets(Model model){
        List<Days> days = dayService.findAll();
        DayTargets target = new DayTargets();
        model.addAttribute("target", target);
        model.addAttribute("days", days);
        return "targets_page";
    }


    @PostMapping("/targets")
    public String addTarget(@ModelAttribute(name = "target") @Valid DayTargets target, BindingResult bindingResult,
                            WeekTargets week, Model model) {
        if (bindingResult.hasErrors()) {
            List<Days> days = dayService.findAll();
            model.addAttribute("days", days);
            return "targets_page";
        } else {
            weekService.save(week);
            targetService.save(target);
        }
        return "redirect:/targets";
    }

}
