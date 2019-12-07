package com.pizzaapp.domain.models.service;

import java.math.BigDecimal;

public class OrderDeliveryServiceModel {

    private String user;
    private AddressServiceModel address;
    private BigDecimal totalPrice;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
