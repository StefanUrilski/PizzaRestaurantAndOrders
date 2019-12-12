package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.items.Drink;
import com.pizzaapp.domain.entities.items.pizza.Pizza;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaAddServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;
import com.pizzaapp.repository.menu.DrinkRepository;
import com.pizzaapp.repository.menu.PizzaRepository;
import com.pizzaapp.service.IngredientService;
import com.pizzaapp.service.MenuService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuServiceTests extends TestBase {

    @MockBean
    PizzaRepository pizzaRepository;

    @MockBean
    DrinkRepository drinkRepository;

    @MockBean
    IngredientService ingredientService;

    @Autowired
    MenuService menuService;

    @Test
    public void addPizza_shouldAddPizza() {
        PizzaAddServiceModel pizza = new PizzaAddServiceModel();
        pizza.setName("pizzaName");

        when(pizzaRepository.findByName("pizzaName"))
                .thenReturn(Optional.empty());

        when(pizzaRepository.saveAndFlush(any()))
                .thenReturn(new Pizza());


        menuService.addPizza(pizza);
        verify(pizzaRepository)
                .save(any());
    }

    @Test
    public void addDrink_shouldAddDrink() {
        DrinkServiceModel drink = new DrinkServiceModel();
        drink.setName("drinkName");

        when(drinkRepository.findByName("drinkName"))
                .thenReturn(Optional.empty());

        when(drinkRepository.saveAndFlush(any()))
                .thenReturn(new Pizza());


        menuService.addDrink(drink);
        verify(drinkRepository)
                .save(any());
    }

    @Test
    public void getAllPizzasOrderedByName_shouldReturnAllPizzas() {
        Pizza p1 = new Pizza();
        Pizza p2 = new Pizza();

        List<Pizza> actual = new ArrayList<>();
        actual.add(p1);
        actual.add(p2);

        when(pizzaRepository.findAllOrderedAlphabetically())
                .thenReturn(actual);

        List<PizzaServiceModel> expected = menuService.getAllPizzasOrderedByName();

        assertEquals(actual.size(), expected.size());
    }

    @Test
    public void getAllDrinksOrderedByName_shouldReturnAllDrinks() {
        Drink p1 = new Drink();
        Drink p2 = new Drink();

        List<Drink> actual = new ArrayList<>();
        actual.add(p1);
        actual.add(p2);

        when(drinkRepository.findAllOrderedAlphabetically())
                .thenReturn(actual);

        List<DrinkServiceModel> expected = menuService.getAllDrinksOrderedByName();

        assertEquals(actual.size(), expected.size());
    }

    @Test
    public void getPizzaById_whenIdExist_shouldReturnSamePizza(){
        Pizza pizza = new Pizza();
        pizza.setName("PizzaName");

        when(pizzaRepository.findById("1"))
                .thenReturn(Optional.of(pizza));

        PizzaServiceModel pizzaService = menuService.getPizzaById("1");

        assertEquals(pizza.getName(), pizzaService.getName());
    }

    @Test
    public void getDrinkById_whenIdExist_shouldReturnSameDrink(){
        Drink drink = new Drink();
        drink.setName("DrinkName");

        when(drinkRepository.findById("1"))
                .thenReturn(Optional.of(drink));

        DrinkServiceModel drinkService = menuService.getDrinkById("1");

        assertEquals(drink.getName(), drinkService.getName());
    }

}
