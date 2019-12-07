package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.OrderCreateServiceModel;
import com.pizzaapp.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(OrderCreateServiceModel orderCreateServiceModel);

    List<OrderServiceModel> getAllOrders();
}
