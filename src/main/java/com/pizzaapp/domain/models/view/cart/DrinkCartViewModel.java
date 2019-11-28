package com.pizzaapp.domain.models.view.cart;

import com.pizzaapp.domain.models.view.menu.DrinkViewModel;

public class DrinkCartViewModel {

    private DrinkViewModel item;
    private int quantity;

    public DrinkViewModel getItem() {
        return item;
    }

    public void setItem(DrinkViewModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
