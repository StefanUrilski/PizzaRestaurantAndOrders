package com.pizzaapp.domain.models.view.cart;

import java.util.List;

public class ShoppingCartItems {

    private List<PizzaCartViewModel> pizzas;
    private List<DrinkCartViewModel> drinks;

    public List<PizzaCartViewModel> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<PizzaCartViewModel> pizzas) {
        this.pizzas = pizzas;
    }

    public List<DrinkCartViewModel> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkCartViewModel> drinks) {
        this.drinks = drinks;
    }
}
