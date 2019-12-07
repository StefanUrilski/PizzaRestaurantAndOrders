package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.OrderAllServiceModel;
import com.pizzaapp.domain.models.service.OrderCreateServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(OrderCreateServiceModel orderCreateServiceModel);

    List<OrderAllServiceModel> getAllOrders();
}
