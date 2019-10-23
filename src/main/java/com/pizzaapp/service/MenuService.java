package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;

import java.util.List;

public interface MenuService {

    void addPizza(PizzaServiceModel pizzaServiceModel);

    void addDrink(DrinkServiceModel drinkServiceModel);

    List<PizzaServiceModel> getPizzaOrderedByName();

    List<DrinkServiceModel> getDrinksOrderedByName();

    PizzaServiceModel getPizzaByName(String name);

    DrinkServiceModel getDrinkByName(String name);

}
