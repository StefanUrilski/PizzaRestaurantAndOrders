package com.pizzaapp.domain.models.service;

import com.pizzaapp.domain.models.service.cart.PizzaOrderServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;

import java.util.List;

public class OrderCartItemsServiceModel {

    private List<PizzaOrderServiceModel> pizzas;
    private List<DrinkServiceModel> drinks;

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
}
