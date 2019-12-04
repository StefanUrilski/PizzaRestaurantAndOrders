package com.pizzaapp.domain.models.service.cart;

public class PizzaCartServiceModel {

    private PizzaOrderServiceModel item;
    private int quantity;

    public PizzaOrderServiceModel getItem() {
        return item;
    }

    public void setItem(PizzaOrderServiceModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
