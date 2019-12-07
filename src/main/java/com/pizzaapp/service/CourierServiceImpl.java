package com.pizzaapp.service;

import com.pizzaapp.domain.entities.Courier;
import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.models.service.OrderServiceModel;
import com.pizzaapp.repository.CourierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository,
                              UserService userService,
                              ModelMapper modelMapper) {
        this.courierRepository = courierRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void takeOrder(String courierEmail, OrderServiceModel orderServiceModel) {
        Courier courier = courierRepository.findCourierByEmail(courierEmail).orElse(null);

        if (courier == null) {
            courier = new Courier(courierEmail);
        }

        courier.getOrders().add(modelMapper.map(orderServiceModel, Order.class));

        courier.setTaken(LocalDateTime.now());

        courierRepository.save(courier);
    }
}
