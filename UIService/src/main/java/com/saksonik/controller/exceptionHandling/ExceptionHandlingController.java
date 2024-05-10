package com.saksonik.controller.exceptionHandling;

import com.saksonik.exception.HeadManagerAPIException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(HeadManagerAPIException.class)
    public String handleHeadManagerAPIException(HeadManagerAPIException e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("code", e.getStatusCode());
        return "errors/base-error";
    }
}
