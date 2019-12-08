package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.order.OrderAllServiceModel;
import com.pizzaapp.domain.models.service.order.OrderCreateServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(OrderCreateServiceModel orderCreateServiceModel);

    List<OrderAllServiceModel> getAllOrders();

    List<OrderAllServiceModel> getAllNonTakenOrdersFromTown(String town);

    OrderAllServiceModel getOrderById(String id);

    boolean takeOrder(String id, String courier);

    void finishOrder(String id);
}
