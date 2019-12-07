package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.OrderDeliveryServiceModel;
import com.pizzaapp.domain.models.service.OrderServiceModel;

import java.util.List;

public interface CourierService {

    void takeOrder(String courierName, OrderServiceModel orderServiceModel);

    List<OrderDeliveryServiceModel> getAllNotFinishedOrders(String courierEmail);
}
