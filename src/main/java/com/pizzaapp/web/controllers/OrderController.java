package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.view.OrderViewModel;
import com.pizzaapp.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ModelAndView allOrders() {
        List<OrderViewModel> allOrders = orderService.getAllOrders().stream()
                .map(order -> modelMapper.map(order, OrderViewModel.class))
                .collect(Collectors.toList());

        return view("/all-orders", "allOrders", allOrders);
    }

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
