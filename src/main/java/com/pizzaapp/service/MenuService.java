package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaAddServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;

import java.util.List;

public interface MenuService {

    void addPizza(PizzaAddServiceModel pizzaAddServiceModel);

    void addDrink(DrinkServiceModel drinkServiceModel);

    List<PizzaServiceModel> getAllPizzasOrderedByName();

    List<DrinkServiceModel> getAllDrinksOrderedByName();

    DrinkServiceModel getDrinkById(String id);

    void editDrink(DrinkServiceModel drinkServiceModel);

    PizzaServiceModel getPizzaById(String id);
}
