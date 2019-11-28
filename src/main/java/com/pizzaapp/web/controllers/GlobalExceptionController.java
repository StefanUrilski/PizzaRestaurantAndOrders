package com.pizzaapp.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class GlobalExceptionController {

//    @ExceptionHandler(RuntimeException.class)
//    public ModelAndView getException(RuntimeException exception) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/error/error-template");
//        modelAndView.addObject("message", exception.getClass().isAnnotationPresent(ResponseStatus.class)
//                ? exception.getClass().getAnnotation(ResponseStatus.class).reason()
//                : "Something went wrong.");
//
//        return modelAndView;
//    }
}
