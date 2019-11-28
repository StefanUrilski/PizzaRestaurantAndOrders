package com.pizzaapp.domain.models.view.cart;

public class PizzaCartViewModel {

    private PizzaOrderViewModel item;
    private int quantity;

    public PizzaOrderViewModel getItem() {
        return item;
    }

    public void setItem(PizzaOrderViewModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
