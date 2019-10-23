package com.pizzaapp.service;


import com.pizzaapp.domain.models.service.ingredients.AllIngredientsServiceModel;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;

import java.util.List;

public interface IngredientService {

    AllIngredientsServiceModel getAllIngredients();

    void addIngredient(IngredientServiceModel ingredientServiceModel);

    <T> List<T> getIngredientsByCategoryOrdered(String category, Class<T> clazz);

    IngredientServiceModel getIngredientByCategoryAndName(String category, String name);
}
