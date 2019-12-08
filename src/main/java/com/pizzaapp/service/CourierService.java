package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.order.OrderDeliveryServiceModel;
import com.pizzaapp.domain.models.service.order.OrderServiceModel;

import java.util.List;

public interface CourierService {

    void takeOrder(String courierName, OrderServiceModel orderServiceModel);

    List<OrderDeliveryServiceModel> getAllNotFinishedOrders(String courierEmail);
}
