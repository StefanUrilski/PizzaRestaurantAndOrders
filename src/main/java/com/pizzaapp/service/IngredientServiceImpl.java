package com.pizzaapp.service;

import com.pizzaapp.domain.entities.items.pizza.Category;
import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import com.pizzaapp.domain.models.service.ingredients.*;
import com.pizzaapp.errors.IngredientAddFailureException;
import com.pizzaapp.repository.menu.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pizzaapp.common.Constants.INGREDIENT_ADD_EXCEPTION;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 CategoryService categoryService,
                                 ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    private <T> List<T> getAllIngredientsMapped(List<Ingredient> allIngredients, String name, Class<T> clazz) {
        return allIngredients.stream()
                .filter(ingredient -> ingredient.getCategory().getName().equals(name))
                .map(ingredient -> modelMapper.map(ingredient, clazz))
                .collect(Collectors.toList());
    }

    @Override
    public AllIngredientsServiceModel getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        AllIngredientsServiceModel model = new AllIngredientsServiceModel();

        model.setMeats(getAllIngredientsMapped(ingredients, "Meat", MeatServiceModel.class));
        model.setCheeses(getAllIngredientsMapped(ingredients, "Cheese", CheeseServiceModel.class));
        model.setVegetables(getAllIngredientsMapped(ingredients, "Vegetable", VegetableServiceModel.class));

        return model;
    }

    @Override
    public void addIngredient(IngredientServiceModel model) {
        Ingredient ingredient = ingredientRepository
                .findByCategoryAndName(model.getCategoryId(), model.getName())
                .orElse(null);

        ExistService.checkIfItemExistThrowException(ingredient, Ingredient.class.getSimpleName());

        ingredient = modelMapper.map(model, Ingredient.class);

        CategoryServiceModel category = categoryService.getCategoryById(model.getCategoryId());

        ingredient.setCategory(modelMapper.map(category, Category.class));

        try {
            ingredientRepository.save(ingredient);
        } catch (Exception ex) {
            throw new IngredientAddFailureException(INGREDIENT_ADD_EXCEPTION);
        }
    }

    @Override
    public <T> List<T> getIngredientsByCategoryOrdered(String name, Class<T> clazz) {
        return ingredientRepository.findAllByCategoryOrderByNameAsc(name)
                .stream()
                .map(dough -> modelMapper.map(dough, clazz))
                .collect(Collectors.toList());
    }

    @Override
    public IngredientServiceModel getIngredientByCategoryAndName(String category, String name) {
        Ingredient ingredient = ingredientRepository.findByCategoryAndName(category, name).orElse(null);

        ExistService.checkIfItemNotExistThrowException(ingredient);

        return modelMapper.map(ingredient, IngredientServiceModel.class);
    }

}
