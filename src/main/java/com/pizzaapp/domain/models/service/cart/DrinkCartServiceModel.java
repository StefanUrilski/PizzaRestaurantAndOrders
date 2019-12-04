package com.pizzaapp.domain.models.service.cart;

import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;

public class DrinkCartServiceModel {

    private DrinkServiceModel item;
    private int quantity;

    public DrinkServiceModel getItem() {
        return item;
    }

    public void setItem(DrinkServiceModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
