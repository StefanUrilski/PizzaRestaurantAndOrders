package com.pizzaapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @GetMapping("/now")
    public ModelAndView orderNow() {
        return view("orders/order-now");
    }

}
