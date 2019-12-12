package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import com.pizzaapp.domain.models.service.cart.DrinkCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaCartServiceModel;
import com.pizzaapp.domain.models.service.order.OrderCreateServiceModel;
import com.pizzaapp.errors.EmptyCartException;
import com.pizzaapp.repository.AddressRepository;
import com.pizzaapp.repository.OrderRepository;
import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.service.OrderService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTests extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    AddressRepository addressRepository;

    @MockBean
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Test(expected = EmptyCartException.class)
    public void createOrder_whenNoDrinks_shouldThrowException() {
        OrderCreateServiceModel order = new OrderCreateServiceModel();

        order.setPizzas(new ArrayList<>());
        order.setDrinks(new ArrayList<>());

        orderService.createOrder(order);
    }

    @Test
    public void createOrder_shouldCreateOrder() {
        OrderCreateServiceModel order = new OrderCreateServiceModel();

        List<PizzaCartServiceModel> pizzas = new ArrayList<>();
        pizzas.add(new PizzaCartServiceModel());
        pizzas.add(new PizzaCartServiceModel());
        order.setPizzas(pizzas);

        List<DrinkCartServiceModel> drinks = new ArrayList<>();
        drinks.add(new DrinkCartServiceModel());
        order.setDrinks(drinks);

        order.setCustomer("userName");
        order.setAddressId("1");

        when(userRepository.findByUsername("userName"))
                .thenReturn(Optional.of(new User()));

        when(addressRepository.findById("1"))
                .thenReturn(Optional.of(new Address()));

        when(orderRepository.saveAndFlush(any()))
                .thenReturn(new Ingredient());

        orderService.createOrder(order);
        verify(orderRepository)
                .save(any());
    }

}
