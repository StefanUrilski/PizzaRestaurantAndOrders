package com.pizzaapp.service;

import com.pizzaapp.domain.entities.Courier;
import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.models.service.order.OrderDeliveryServiceModel;
import com.pizzaapp.domain.models.service.order.OrderServiceModel;
import com.pizzaapp.repository.CourierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository,
                              ModelMapper modelMapper) {
        this.courierRepository = courierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void takeOrder(String courierEmail, OrderServiceModel orderServiceModel) {
        Courier courier = courierRepository.findCourierByEmail(courierEmail).orElse(null);

        if (courier == null) {
            courier = new Courier(courierEmail);
        }
        courier.getOrders().add(modelMapper.map(orderServiceModel, Order.class));

        courierRepository.save(courier);
    }

    @Override
    public List<OrderDeliveryServiceModel> getAllNotFinishedOrders(String courierEmail) {
        Courier courier = courierRepository.findCourierByEmail(courierEmail).orElse(null);

        if (courier == null || courier.getOrders() == null) {
            return null;
        }

        return courier.getOrders().stream()
                .filter(order -> order.isFinished() == null)
                .map(order -> {
                    OrderDeliveryServiceModel currOrder = modelMapper.map(order, OrderDeliveryServiceModel.class);
                    currOrder.setUser(order.getUser().getFullName());
                    return currOrder;
                })
                .collect(Collectors.toList());
    }
}
