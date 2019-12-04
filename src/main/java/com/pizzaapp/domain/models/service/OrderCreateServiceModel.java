package com.pizzaapp.domain.models.service;

import com.pizzaapp.domain.models.service.cart.DrinkCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaCartServiceModel;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreateServiceModel {

    private String customer;
    private String addressId;
    private List<PizzaCartServiceModel> pizzas;
    private List<DrinkCartServiceModel> drinks;
    private BigDecimal totalPrice;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<PizzaCartServiceModel> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaCartServiceModel> pizzas) {
        this.pizzas = pizzas;
    }

    public List<DrinkCartServiceModel> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkCartServiceModel> drinks) {
        this.drinks = drinks;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
