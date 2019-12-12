package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.Courier;
import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.models.service.order.OrderServiceModel;
import com.pizzaapp.repository.CourierRepository;
import com.pizzaapp.service.CourierService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourierServiceTests extends TestBase {

    @MockBean
    CourierRepository courierRepository;

    @Autowired
    CourierService courierService;

    @Test
    public void takeOrder_shouldTakeTheOrder() {
        Courier courier = new Courier();

        courier.setOrders(List.of(new Order()));

        when(courierRepository.findCourierByEmail("email"))
                .thenReturn(Optional.of(courier));

        courierService.takeOrder("someName", new OrderServiceModel());

        verify(courierRepository)
                .save(any());
    }
}
