package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.items.pizza.Category;
import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import com.pizzaapp.domain.models.service.ingredients.AllIngredientsServiceModel;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.errors.PropertyNotFoundException;
import com.pizzaapp.repository.menu.CategoryRepository;
import com.pizzaapp.repository.menu.IngredientRepository;
import com.pizzaapp.service.IngredientService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceTests extends TestBase {

    @MockBean
    IngredientRepository ingredientRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    IngredientService service;

    private List<Ingredient> getIngredients() {
        Ingredient first = new Ingredient();
        Ingredient second = new Ingredient();
        Ingredient third = new Ingredient();

        first.setName("Bacon");
        first.setCategory(new Category("Meat"));

        second.setName("Mozzarella");
        second.setCategory(new Category("Cheese"));

        third.setName("Tomato");
        third.setCategory(new Category("Vegetable"));

        ArrayList<Ingredient> ingredient = new ArrayList<>();

        ingredient.add(first);
        ingredient.add(second);
        ingredient.add(third);

        return ingredient;
    }

    @Test
    public void getAllIngredients_shouldReturnAllIngredientsDividedByCategory() {
        List<Ingredient> ingredients = getIngredients();

        when(ingredientRepository.findAllOrderByName())
                .thenReturn(ingredients);

        AllIngredientsServiceModel allIngredients = service.getAllIngredients();

        assertEquals(ingredients.get(0).getName(), allIngredients.getMeats().get(0).getName());
        assertEquals(ingredients.get(1).getName(), allIngredients.getCheeses().get(0).getName());
        assertEquals(ingredients.get(2).getName(), allIngredients.getVegetables().get(0).getName());
    }

    @Test
    public void addIngredient_shouldAddIngredient() {
        IngredientServiceModel ingredient = new IngredientServiceModel();
        ingredient.setId("1");
        ingredient.setName("ingredientName");
        ingredient.setCategoryId("2");

        when(ingredientRepository.findByCategoryAndName("3", "ingredientName"))
                .thenReturn(Optional.empty());

        Category category = new Category();
        category.setId("2");
        category.setName("categoryName");

        when(categoryRepository.findById("2"))
                .thenReturn(Optional.of(category));

        when(ingredientRepository.saveAndFlush(any()))
                .thenReturn(new Ingredient());

        service.addIngredient(ingredient);
        verify(ingredientRepository)
                .save(any());
    }

    @Test(expected = PropertyNotFoundException.class)
    public void getIngredientsByIds_whenDontFindAnyIngredients_shouldThrowException() {
        List<String> ingredientIds = new ArrayList<>() {{
            add("1");
            add("2");
            add("3");
        }};

        when(ingredientRepository.findAll())
                .thenReturn(new ArrayList<>());

        service.getIngredientsByIds(ingredientIds);
    }

    @Test
    public void getIngredientById_whenIdExist_shouldReturnSameIngredient(){
        Ingredient ingredient = new Ingredient();
        ingredient.setName("IngredientName");

        Mockito.when(ingredientRepository.findById("1"))
                .thenReturn(Optional.of(ingredient));

        IngredientServiceModel ingredientService = service.getIngredientById("1");

        assertEquals(ingredient.getName(), ingredientService.getName());
    }
}
