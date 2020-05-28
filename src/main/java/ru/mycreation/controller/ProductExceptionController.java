package ru.mycreation.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler
    public String exception(Exception exception, Model model) {
        model.addAttribute("exception", exception.getMessage());
        return "exception";
    }
}
