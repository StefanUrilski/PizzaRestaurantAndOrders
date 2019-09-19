package com.pizzaapp.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String faviconPath = "https://res.cloudinary.com/rado-cloud/image/upload/v1535556277/pizza-favicon.jpg";

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }

        modelAndView.addObject("favicon", faviconPath);
    }
}
