package com.coffee.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    public String handleDuplicateUser(DuplicateException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "signup";
    }

    @ExceptionHandler(NotEqualException.class)
    public String handleDuplicateUser(NotEqualException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "signup";
    }

}
