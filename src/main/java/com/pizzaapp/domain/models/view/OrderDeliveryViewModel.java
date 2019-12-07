package com.pizzaapp.domain.models.view;

import com.pizzaapp.domain.models.view.user.AddressViewModel;

import java.math.BigDecimal;

public class OrderDeliveryViewModel {

    private String id;
    private String user;
    private AddressViewModel address;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
