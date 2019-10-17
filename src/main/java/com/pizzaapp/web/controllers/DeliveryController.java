package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.view.user.AddressViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {

    @GetMapping("/delivery/{town}")
    public ModelAndView delivery(@PathVariable String town, ModelAndView modelAndView) {
//        DeliveryAddressViewModel
        return view("delivery/all-orders", "allOrders", null);
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable String id, ModelAndView modelAndView) {
//        DeliveryFullAddressViewModel
        return view("delivery/orders-details", "order", null);
    }

    @GetMapping("/take/{id}")
    public ModelAndView take(@PathVariable String id, ModelAndView modelAndView) {
        // TODO: 16-Oct-19 missing logic
        return view("delivery/all-orders");
    }

}
