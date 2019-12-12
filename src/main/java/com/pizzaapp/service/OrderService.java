package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.order.OrderFullServiceModel;
import com.pizzaapp.domain.models.service.order.OrderCreateServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(OrderCreateServiceModel orderCreateServiceModel);

    List<OrderFullServiceModel> getAllOrders();

    List<OrderFullServiceModel> getAllNonTakenOrdersFromTown(String town);

    OrderFullServiceModel getOrderById(String id);

    boolean takeOrder(String id, String courier);

    void finishOrder(String id);
}
