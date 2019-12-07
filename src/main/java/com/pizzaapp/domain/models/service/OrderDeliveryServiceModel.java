package com.pizzaapp.domain.models.service;

import java.math.BigDecimal;

public class OrderDeliveryServiceModel {

    private UserServiceModel user;
    private AddressServiceModel address;
    private BigDecimal totalPrice;

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
