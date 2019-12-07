package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.items.Drink;
import com.pizzaapp.domain.entities.items.pizza.Pizza;
import com.pizzaapp.domain.models.service.*;
import com.pizzaapp.domain.models.service.cart.DrinkCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaOrderServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.errors.EmptyCartException;
import com.pizzaapp.errors.OrderFailureException;
import com.pizzaapp.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(UserService userService,
                            AddressService addressService,
                            OrderRepository orderRepository,
                            ModelMapper modelMapper) {
        this.userService = userService;
        this.addressService = addressService;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    private List<DrinkServiceModel> prepareDrinks(List<DrinkCartServiceModel> drinks) {
        List<DrinkServiceModel> drinksCartItems = new ArrayList<>();

        drinks.forEach(drink ->
              IntStream
                      .range(0, drink.getQuantity())
                      .forEach(iter ->
                              drinksCartItems.add(drink.getItem())
                      )
        );

        return drinksCartItems;
    }

    private List<PizzaOrderServiceModel> preparePizzas(List<PizzaCartServiceModel> pizzas) {
        List<PizzaOrderServiceModel> pizzasCartItems = new ArrayList<>();

        pizzas.forEach(pizza ->
                IntStream
                        .range(0, pizza.getQuantity())
                        .forEach(iter ->
                                pizzasCartItems.add(pizza.getItem())
                        )
        );

        return pizzasCartItems;
    }

    private OrderCartItemsServiceModel prepareOrder(List<PizzaCartServiceModel> pizzas, List<DrinkCartServiceModel> drinks) {
        OrderCartItemsServiceModel cart = new OrderCartItemsServiceModel();

        if (!pizzas.isEmpty()) {
            cart.setPizzas(preparePizzas(pizzas));
        }

        if (!drinks.isEmpty()) {
            cart.setDrinks(prepareDrinks(drinks));
        }

        return cart;
    }

    @Override
    public void createOrder(OrderCreateServiceModel orderService) {
        if (orderService.getDrinks().isEmpty() && orderService.getPizzas().isEmpty()) {
            throw new EmptyCartException(Constants.EMPTY_CART_EXCEPTION);
        }

        UserServiceModel user = userService.extractUserByEmail(orderService.getCustomer());
        AddressServiceModel address = addressService.getAddressById(orderService.getAddressId());

        OrderCartItemsServiceModel cart = prepareOrder(orderService.getPizzas(), orderService.getDrinks());
        Order order = new Order();

        order.setUser(modelMapper.map(user, User.class));
        order.setAddress(modelMapper.map(address, Address.class));
        order.setOrderedOn(LocalDateTime.now());
        order.setTotalPrice(orderService.getTotalPrice());

        order.setPizzas(
                cart.getPizzas().stream()
                .map(pizza -> modelMapper.map(pizza, Pizza.class))
                .collect(Collectors.toList())
        );

        order.setDrinks(
                cart.getDrinks().stream()
                        .map(drink -> modelMapper.map(drink, Drink.class))
                        .collect(Collectors.toList())
        );

        try {
            orderRepository.save(order);
        } catch (Exception ex) {
            throw new OrderFailureException(Constants.ORDER_ADD_EXCEPTION);
        }
    }

    @Override
    public List<OrderServiceModel> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
