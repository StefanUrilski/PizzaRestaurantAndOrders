package com.pizzaapp.domain.models.view.order;

import com.pizzaapp.domain.models.view.user.AddressViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderViewModel {

    private String id;
    private String user;
    private AddressViewModel address;
    private LocalDateTime orderedOn;
    private boolean isTaken;
    private boolean isFinished;
    private String pizzas;
    private String drinks;
    private BigDecimal totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public AddressViewModel getAddress() {
        return address;
    }

    public void setAddress(AddressViewModel address) {
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

    public String getPizzas() {
        return pizzas;
    }

    public void setPizzas(String pizzas) {
        this.pizzas = pizzas;
    }

    public String getDrinks() {
        return drinks;
    }

    public void setDrinks(String drinks) {
        this.drinks = drinks;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
