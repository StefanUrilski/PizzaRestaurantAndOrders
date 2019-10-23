package com.pizzaapp.service;

import com.pizzaapp.domain.entities.Ingredient;
import com.pizzaapp.domain.models.service.ingredients.AllIngredientsServiceModel;
import com.pizzaapp.errors.IngredientAddFailureException;
import com.pizzaapp.errors.NameNotFoundException;
import com.pizzaapp.repository.menu.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.models.service.ingredients.*;
import com.pizzaapp.errors.IngredientAlreadyExistsException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.pizzaapp.common.Constants.INGREDIENT_ADD_EXCEPTION;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    private <T> List<T> getAllIngredientsMapped(List<Ingredient> allIngredients, String name, Class<T> clazz) {
        return allIngredients.stream()
                .filter(ingredient -> ingredient.getCategory().getName().equals(name))
                .map(ingredient -> modelMapper.map(ingredient, clazz))
                .collect(Collectors.toList());
    }

    private void checkIfExist(Object entity, String name) {
        if (entity != null) {
            throw new IngredientAlreadyExistsException(String.format(Constants.INGREDIENT_ALREADY_EXISTS, name));
        }
    }

    private void checkIfExist(Object entity) {
        if (entity == null) {
            throw new NameNotFoundException(Constants.WRONG_NON_EXISTENT_NAME);
        }
    }

    @Override
    public AllIngredientsServiceModel getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        AllIngredientsServiceModel model = new AllIngredientsServiceModel();

        model.setCheeses(getAllIngredientsMapped(ingredients, "Cheese", CheeseServiceModel.class));
        model.setVegetables(getAllIngredientsMapped(ingredients, "Vegetable", VegetableServiceModel.class));
        model.setDoughs(getAllIngredientsMapped(ingredients, "Dough", DoughServiceModel.class));
        model.setMeats(getAllIngredientsMapped(ingredients, "Meat", MeatServiceModel.class));
        model.setSizes(getAllIngredientsMapped(ingredients, "Size", SizeServiceModel.class));


        return model;
    }

    @Override
    public void addIngredient(IngredientServiceModel model) {
        Ingredient ingredient = ingredientRepository
                .findByCategoryAndName(model.getCategory(), model.getName())
                .orElse(null);

        checkIfExist(ingredient, Ingredient.class.getSimpleName());

        ingredient = modelMapper.map(model, Ingredient.class);

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

        checkIfExist(ingredient);

        return modelMapper.map(ingredient, IngredientServiceModel.class);
    }

}
