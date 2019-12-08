package com.pizzaapp.domain.models.service.order;

import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaOrderServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private UserServiceModel user;
    private AddressServiceModel address;
    private LocalDateTime orderedOn;
    private boolean isTaken;
    private boolean isFinished;
    private List<PizzaOrderServiceModel> pizzas;
    private List<DrinkServiceModel> drinks;
    private BigDecimal totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public AddressServiceModel getAddress() {
        return address;
    }

    public void setAddress(AddressServiceModel address) {
        this.address = address;
    }

    public LocalDateTime getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<PizzaOrderServiceModel> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaOrderServiceModel> pizzas) {
        this.pizzas = pizzas;
    }

    public List<DrinkServiceModel> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkServiceModel> drinks) {
        this.drinks = drinks;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
