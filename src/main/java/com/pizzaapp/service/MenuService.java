package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaAddServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;

import java.util.List;

public interface MenuService {

    void addPizza(PizzaAddServiceModel pizzaAddServiceModel);

    void addDrink(DrinkServiceModel drinkServiceModel);

    List<PizzaServiceModel> getPizzaOrderedByName();

    List<DrinkServiceModel> getDrinksOrderedByName();

    DrinkServiceModel getDrinkById(String id);

    PizzaServiceModel getPizzaByName(String name);

    DrinkServiceModel getDrinkByName(String name);

}
