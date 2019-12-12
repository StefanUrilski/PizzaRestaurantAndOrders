package com.pizzaapp.service.integration;

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

}
