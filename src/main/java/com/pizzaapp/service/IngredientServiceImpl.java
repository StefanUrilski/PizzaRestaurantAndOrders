package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.items.pizza.Category;
import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import com.pizzaapp.domain.models.service.ingredients.AllIngredientsServiceModel;
import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.errors.IngredientAddFailureException;
import com.pizzaapp.errors.PropertyNotFoundException;
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

    private <T> List<IngredientServiceModel> getAllIngredientsMapped(List<Ingredient> allIngredients, String name) {
        return allIngredients.stream()
                .filter(ingredient -> ingredient.getCategory().getName().equals(name))
                .map(ingredient -> modelMapper.map(ingredient, IngredientServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public AllIngredientsServiceModel getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAllOrderByName();

        AllIngredientsServiceModel model = new AllIngredientsServiceModel();

        model.setMeats(getAllIngredientsMapped(ingredients, "Meat"));
        model.setCheeses(getAllIngredientsMapped(ingredients, "Cheese"));
        model.setVegetables(getAllIngredientsMapped(ingredients, "Vegetable"));

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
    public List<IngredientServiceModel> getIngredientsByIds(List<String> ingredientIds) {
        List<Ingredient> allIngredients = ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredientIds.contains(ingredient.getId()))
                .collect(Collectors.toList());

        if (allIngredients.size() != ingredientIds.size()) {
            throw new PropertyNotFoundException(Constants.WRONG_NON_EXISTENT_INGREDIENT);
        }

        return allIngredients.stream()
                .map(ingredient -> modelMapper.map(ingredient, IngredientServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public IngredientServiceModel getIngredientById(String id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(ingredient);

        return modelMapper.map(ingredient, IngredientServiceModel.class);
    }
}
