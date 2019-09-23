package com.pizzaapp.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String view, String objectName, Object object) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("view", view);
        modelAndView.addObject(objectName, object);

        modelAndView.setViewName("fragments/base-layout");

        return modelAndView;
    }

    protected ModelAndView view(String view) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("view", view);

        modelAndView.setViewName("fragments/base-layout");

        return modelAndView;
    }

    protected ModelAndView redirect(String url) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:" + url);

        return modelAndView;
    }
}

