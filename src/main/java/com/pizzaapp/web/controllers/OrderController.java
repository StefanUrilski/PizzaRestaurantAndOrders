package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.service.OrderDeliveryServiceModel;
import com.pizzaapp.domain.models.view.OrderDeliveryViewModel;
import com.pizzaapp.domain.models.view.OrderViewModel;
import com.pizzaapp.service.CourierService;
import com.pizzaapp.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
@PreAuthorize("hasRole('ROLE_COURIER') && !hasRole('ROLE_MODERATOR')")
public class OrderController extends BaseController {

    private final OrderService orderService;
    private final CourierService courierService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService,
                           CourierService courierService,
                           ModelMapper modelMapper) {
        this.orderService = orderService;
        this.courierService = courierService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allOrders() {
        List<OrderViewModel> allOrders = orderService.getAllOrders().stream()
                .map(order -> modelMapper.map(order, OrderViewModel.class))
                .collect(Collectors.toList());

        return view("/orders/orders-all", "allOrders", allOrders);
    }

    @GetMapping("/delivery/{town}")
    public ModelAndView delivery(@PathVariable String town) {
        List<OrderViewModel> allOrders = orderService.getAllNonTakenOrdersFromTown(town).stream()
                .map(order -> modelMapper.map(order, OrderViewModel.class))
                .collect(Collectors.toList());

        return view("orders/for-delivery", "allOrders", allOrders);
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable String id) {
        OrderViewModel order = modelMapper.map(orderService.getOrderById(id), OrderViewModel.class);

        return view("orders/order-details", "order", order);
    }

    @PostMapping("/take/{id}")
    public ModelAndView takeOrderConfirm(@PathVariable String id, Principal principal) {
        orderService.takeOrder(id, principal.getName());

        return redirect("/");
    }

    @GetMapping("/my")
    public ModelAndView myOrders(Principal principal) {
        List<OrderDeliveryServiceModel> orders = courierService.getAllNotFinishedOrders(principal.getName());

        if (orders == null) {
            return view("orders/orders-my", "allOrder", null);
        }

        List<OrderDeliveryViewModel> allOrder = orders.stream()
                .map(order -> modelMapper.map(order, OrderDeliveryViewModel.class))
                .collect(Collectors.toList());

        return view("orders/orders-my", "allOrder", allOrder);
    }

    @PostMapping("finish/{id}")
    public ModelAndView finishConfirm(@PathVariable String id) {
        orderService.finishOrder(id);

        return redirect("orders/my");
    }
}
