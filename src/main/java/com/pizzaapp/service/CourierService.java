package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.OrderServiceModel;

public interface CourierService {

    void takeOrder(String courierName, OrderServiceModel orderServiceModel);
}
