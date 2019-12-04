package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.OrderCreateServiceModel;

public interface OrderService {

    void createOrder(OrderCreateServiceModel orderCreateServiceModel);

}
