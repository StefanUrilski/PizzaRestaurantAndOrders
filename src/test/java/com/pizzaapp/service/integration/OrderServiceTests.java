package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.Courier;
import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.items.Drink;
import com.pizzaapp.domain.entities.items.pizza.Pizza;
import com.pizzaapp.domain.models.service.cart.DrinkCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaCartServiceModel;
import com.pizzaapp.domain.models.service.order.OrderCreateServiceModel;
import com.pizzaapp.domain.models.service.order.OrderFullServiceModel;
import com.pizzaapp.errors.EmptyCartException;
import com.pizzaapp.repository.AddressRepository;
import com.pizzaapp.repository.CourierRepository;
import com.pizzaapp.repository.OrderRepository;
import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.service.OrderService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @MockBean
    CourierRepository courierRepository;

    @Autowired
    OrderService orderService;


    private List<Order> getOrders() {
        User user = new User();
        user.setFullName("UserFullName");
        Order order = new Order();
        order.setUser(user);

        order.setPizzas(getPizzas());
        order.setDrinks(getDrinks());

        return new ArrayList<>(List.of(order));
    }

    private List<Pizza> getPizzas() {
        Pizza pizza = new Pizza();
        pizza.setName("Peperoni");
        return new ArrayList<>(List.of(pizza));
    }

    private List<Drink> getDrinks() {
        Drink drink = new Drink();
        drink.setName("Beer");
        return new ArrayList<>(List.of(drink));
    }


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

        orderService.createOrder(order);
        verify(orderRepository)
                .save(any());
    }

    @Test
    public void getAllOrders_shouldReturnAllOrders() {
        List<Order> orders = getOrders();

        when(orderRepository.findAll())
                .thenReturn(orders);

        List<OrderFullServiceModel> allOrders = orderService.getAllOrders();

        assertEquals(orders.size(), allOrders.size());
        assertEquals(orders.get(0).getUser().getFullName(), allOrders.get(0).getUser());
    }

    @Test
    public void getOrderById_whenIdExist_shouldReturnSameOrder(){
        User user = new User();
        user.setFullName("Some Name");

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(BigDecimal.TEN);
        order.setPizzas(getPizzas());
        order.setDrinks(getDrinks());

        when(orderRepository.findById("1"))
                .thenReturn(Optional.of(order));

        OrderFullServiceModel fullOrder = orderService.getOrderById("1");

        assertEquals(order.getUser().getFullName(), fullOrder.getUser());
        assertEquals(order.getTotalPrice(), fullOrder.getTotalPrice());
    }

    @Test
    public void takeOrder_whenOrderIsTaken_shouldReturnFalse() {
        Order order = new Order();
        order.setTaken(true);

        when(orderRepository.findById("1"))
                .thenReturn(Optional.of(order));

        when(courierRepository.findCourierByEmail("courierEmail"))
                .thenReturn(Optional.of(new Courier()));

        assertFalse(orderService.takeOrder("1", "courier"));
    }

    @Test
    public void takeOrder_whenIdAndCourierExist_shouldTakeTheOrder() {
        Order order = new Order();

        when(orderRepository.findById("1"))
                .thenReturn(Optional.of(order));

        when(courierRepository.findCourierByEmail("courierEmail"))
                .thenReturn(Optional.of(new Courier()));

        orderService.takeOrder("1", "courier");

        assertTrue(order.isTaken());
    }

    @Test
    public void finishOrder_whenIdExist_shouldFinishTheOrder() {
        Order order = new Order();

        when(orderRepository.findById("1"))
                .thenReturn(Optional.of(order));

        orderService.finishOrder("1");

        assertTrue(order.isFinished());
    }
}
