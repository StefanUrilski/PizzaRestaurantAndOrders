package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.entities.Town;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.items.Drink;
import com.pizzaapp.domain.entities.items.MenuItem;
import com.pizzaapp.domain.entities.items.pizza.Pizza;
import com.pizzaapp.domain.models.service.*;
import com.pizzaapp.domain.models.service.cart.DrinkCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaOrderServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.order.OrderFullServiceModel;
import com.pizzaapp.domain.models.service.order.OrderCartItemsServiceModel;
import com.pizzaapp.domain.models.service.order.OrderCreateServiceModel;
import com.pizzaapp.domain.models.service.order.OrderServiceModel;
import com.pizzaapp.errors.EmptyCartException;
import com.pizzaapp.errors.OrderFailureException;
import com.pizzaapp.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final CourierService courierService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(UserService userService,
                            AddressService addressService,
                            OrderRepository orderRepository,
                            CourierService courierService,
                            ModelMapper modelMapper) {
        this.userService = userService;
        this.addressService = addressService;
        this.orderRepository = orderRepository;
        this.courierService = courierService;
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

    @SuppressWarnings("unchecked")
    private <T> String calcItems(List<T> items) {
        Map<String, Integer> itemsCount = new LinkedHashMap<>();

        ((List<MenuItem>) items)
                .forEach(item -> {
                    String name = item.getName();
                    itemsCount.putIfAbsent(name, 0);
                    itemsCount.put(name, itemsCount.get(name) + 1);
                });

        return itemsCount.entrySet().stream()
                .map(entry -> String.format("%s x %s", entry.getValue(), entry.getKey()))
                .collect(Collectors.joining(", "));
    }

    private List<OrderFullServiceModel> mapToAllOrderService(List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    OrderFullServiceModel currOrder = modelMapper.map(order, OrderFullServiceModel.class);
                    currOrder.setUser(order.getUser().getFullName());

                    currOrder.setPizzas(calcItems(order.getPizzas()));
                    currOrder.setDrinks(calcItems(order.getDrinks()));

                    return currOrder;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void createOrder(OrderCreateServiceModel orderService) {
        if (orderService.getDrinks().isEmpty() && orderService.getPizzas().isEmpty()) {
            throw new EmptyCartException(Constants.EMPTY_CART_EXCEPTION);
        }

        UserServiceModel user = userService.getUserByEmail(orderService.getCustomer());
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
    public List<OrderFullServiceModel> getAllOrders() {
        return mapToAllOrderService(orderRepository.findAll());
    }

    @Override
    public List<OrderFullServiceModel> getAllNonTakenOrdersFromTown(String town) {
        return mapToAllOrderService(orderRepository.findAllByTownAndIfTaken(Town.valueOf(town), false));
    }

    @Override
    public OrderFullServiceModel getOrderById(String id) {
        Order order = orderRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(order);

        OrderFullServiceModel serviceModel = modelMapper.map(order, OrderFullServiceModel.class);

        serviceModel.setUser(order.getUser().getFullName());

        serviceModel.setPizzas(calcItems(order.getPizzas()));
        serviceModel.setDrinks(calcItems(order.getDrinks()));

        return serviceModel;
    }

    @Override
    public boolean takeOrder(String id, String courier) {
        Order order = orderRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(order);

        if (order.isTaken()) {
            return false;
        }

        courierService.takeOrder(courier, modelMapper.map(order, OrderServiceModel.class));

        order.setTaken(true);

        try {
            orderRepository.save(order);
            return true;
        } catch (Exception ex) {
            throw new OrderFailureException(Constants.ORDER_ADD_EXCEPTION);
        }
    }

    @Override
    public void finishOrder(String id) {
        Order order = orderRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(order);

        order.setFinished(true);

        orderRepository.save(order);
    }
}
