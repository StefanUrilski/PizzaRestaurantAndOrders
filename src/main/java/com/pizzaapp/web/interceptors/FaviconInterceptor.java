package com.pizzaapp.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String faviconPath = "https://res.cloudinary.com/stefan-cloud/image/upload/v1568996236/iindc2ktj5k8chk7w0ue.jpg";

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }

        modelAndView.addObject("favicon", faviconPath);
    }
}
