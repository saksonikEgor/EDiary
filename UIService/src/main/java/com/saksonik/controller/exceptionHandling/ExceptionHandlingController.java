package com.saksonik.controller.exceptionHandling;

import com.saksonik.exception.WrongRequestException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/base-error";
    }

    @ExceptionHandler(WrongRequestException.class)
    public String handleWrongRequestException(WrongRequestException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/base-error";
    }
}
