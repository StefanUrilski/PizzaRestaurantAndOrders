package com.pizzaapp.service;

import com.pizzaapp.domain.entities.items.Drink;
import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import com.pizzaapp.domain.entities.items.pizza.Pizza;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaAddServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;
import com.pizzaapp.errors.ItemAddFailureException;
import com.pizzaapp.repository.menu.DrinkRepository;
import com.pizzaapp.repository.menu.PizzaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pizzaapp.common.Constants.ITEM_ADD_EXCEPTION;

@Service
public class MenuServiceImpl implements MenuService {

    private final PizzaRepository pizzaRepository;
    private final DrinkRepository drinkRepository;
    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuServiceImpl(PizzaRepository pizzaRepository,
                           DrinkRepository drinkRepository,
                           IngredientService ingredientService,
                           ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientService = ingredientService;
        this.modelMapper = modelMapper;
    }

    private String getDescriptionText(Set<Ingredient> ingredients) {
        String ingredientNamesToLower = ingredients.stream()
                .map(Ingredient::getName)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));

        return String.format("Pizza sauce, %s and a pinch of love.", ingredientNamesToLower);
    }

    @Override
    public void addPizza(PizzaAddServiceModel pizzaAddServiceModel) {
        Pizza pizza = pizzaRepository.findByName(pizzaAddServiceModel.getName()).orElse(null);

        ExistService.checkIfItemExistThrowException(pizza, Pizza.class.getSimpleName());

        pizza = modelMapper.map(pizzaAddServiceModel, Pizza.class);

        // Getting IngredientServiceModel from given ingredientsIds.
        List<IngredientServiceModel> ingredientsServiceModel =
                ingredientService.getIngredientsByIds(pizzaAddServiceModel.getIngredientsIds());

        // Mapping IngredientServiceModel to Ingredient class.
        Set<Ingredient> ingredients = ingredientsServiceModel.stream()
                .map(ingredientServiceModel -> modelMapper.map(ingredientServiceModel, Ingredient.class))
                .collect(Collectors.toSet());

        pizza.setIngredients(ingredients);


        BigDecimal price = ingredientsServiceModel.stream()
                .map(IngredientServiceModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pizza.setPrice(price);

        pizza.setDescription(getDescriptionText(pizza.getIngredients()));

        try {
            pizzaRepository.save(pizza);
        } catch (Exception ex) {
            throw new ItemAddFailureException(ITEM_ADD_EXCEPTION);
        }
    }

    @Override
    public void addDrink(DrinkServiceModel drinkServiceModel) {
        Drink drink = drinkRepository.findByName(drinkServiceModel.getName()).orElse(null);

        ExistService.checkIfItemExistThrowException(drink, Drink.class.getSimpleName());

        drink = modelMapper.map(drinkServiceModel, Drink.class);

        try {
            drinkRepository.save(drink);
        } catch (Exception ex) {
            throw new ItemAddFailureException(ITEM_ADD_EXCEPTION);
        }
    }

    @Override
    public List<PizzaServiceModel> getPizzaOrderedByName() {
        return pizzaRepository.findAllOrderedAlphabetically()
                .stream()
                .map(pizza -> modelMapper.map(pizza, PizzaServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DrinkServiceModel> getDrinksOrderedByName() {
        return drinkRepository.findAllOrderedAlphabetically()
                .stream()
                .map(drink -> modelMapper.map(drink, DrinkServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DrinkServiceModel getDrinkById(String id) {
        Drink drink = drinkRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(drink);

        return modelMapper.map(drink, DrinkServiceModel.class);
    }

    @Override
    public PizzaServiceModel getPizzaByName(String name) {
        Pizza pizza = pizzaRepository.findByName(name).orElse(null);

        ExistService.checkIfItemNotExistThrowException(pizza);

        return modelMapper.map(pizza, PizzaServiceModel.class);
    }

    @Override
    public DrinkServiceModel getDrinkByName(String name) {
        Drink drink = drinkRepository.findByName(name).orElse(null);

        ExistService.checkIfItemNotExistThrowException(drink);

        return modelMapper.map(drink, DrinkServiceModel.class);
    }

}
