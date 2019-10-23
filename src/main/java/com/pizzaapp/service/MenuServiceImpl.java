package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.items.Drink;
import com.pizzaapp.domain.entities.items.Pizza;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;
import com.pizzaapp.errors.IngredientAddFailureException;
import com.pizzaapp.errors.ItemAddFailureException;
import com.pizzaapp.errors.ItemAlreadyExistsException;
import com.pizzaapp.errors.NameNotFoundException;
import com.pizzaapp.repository.menu.DrinkRepository;
import com.pizzaapp.repository.menu.PizzaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pizzaapp.common.Constants.ITEM_ADD_EXCEPTION;

@Service
public class MenuServiceImpl implements MenuService {

    private final PizzaRepository pizzaRepository;
    private final DrinkRepository drinkRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuServiceImpl(PizzaRepository pizzaRepository,
                           DrinkRepository drinkRepository,
                           ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.drinkRepository = drinkRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addPizza(PizzaServiceModel pizzaServiceModel) {
        Pizza pizza = pizzaRepository.findByName(pizzaServiceModel.getName()).orElse(null);

        checkIfExist(pizza, Pizza.class.getSimpleName());

        pizza = modelMapper.map(pizzaServiceModel, Pizza.class);

        try {
            pizzaRepository.save(pizza);
        } catch (Exception ex) {
            throw new ItemAddFailureException(ITEM_ADD_EXCEPTION);
        }
    }

    @Override
    public void addDrink(DrinkServiceModel drinkServiceModel) {
        Drink drink = drinkRepository.findByName(drinkServiceModel.getName()).orElse(null);

        checkIfExist(drink, Drink.class.getSimpleName());

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
    public PizzaServiceModel getPizzaByName(String name) {
        Pizza pizza = pizzaRepository.findByName(name).orElse(null);

        checkIfExist(pizza);

        return modelMapper.map(pizza, PizzaServiceModel.class);
    }

    @Override
    public DrinkServiceModel getDrinkByName(String name) {
        Drink drink = drinkRepository.findByName(name).orElse(null);

        checkIfExist(drink);

        return modelMapper.map(drink, DrinkServiceModel.class);
    }

    private void checkIfExist(Object item, String itemName) {
        if (item != null) {
            throw new ItemAlreadyExistsException(String.format(Constants.ITEM_ALREADY_EXISTS, itemName));
        }
    }

    private void checkIfExist(Object item) {
        if (item == null) {
            throw new NameNotFoundException(Constants.WRONG_NON_EXISTENT_NAME);
        }
    }
}
