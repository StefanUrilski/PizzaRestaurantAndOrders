package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.menu.DrinkEditBindingModel;
import com.pizzaapp.domain.models.binding.menu.IngredientBindingModel;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.domain.models.view.ingredients.IngredientViewModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.view.ingredients.AllIngredientsViewModel;
import com.pizzaapp.domain.models.view.menu.DrinkViewModel;
import com.pizzaapp.domain.models.view.menu.PizzaViewModel;
import com.pizzaapp.service.IngredientService;
import com.pizzaapp.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
@PreAuthorize("hasRole('ROLE_MODERATOR')")
public class ItemsController extends BaseController {

    private final MenuService menuService;
    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemsController(MenuService menuService,
                           IngredientService ingredientService,
                           ModelMapper modelMapper) {
        this.menuService = menuService;
        this.ingredientService = ingredientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/drinks/all")
    public ModelAndView allDrinks() {
        List<DrinkViewModel> drinks = menuService.getAllDrinksOrderedByName().stream()
                .map(drink -> modelMapper.map(drink, DrinkViewModel.class))
                .collect(Collectors.toList());

        return view("items/drinks-all", "drinks", drinks);
    }

    @GetMapping("/drinks/edit/{id}")
    public ModelAndView editDrink(@PathVariable String id) {
        DrinkViewModel drinkViewModel = modelMapper.map(menuService.getDrinkById(id), DrinkViewModel.class);

        return view("items/drink-edit", "drink", drinkViewModel);
    }

    @PostMapping("/drinks/edit/*")
    public ModelAndView editDrinkConfirm(@ModelAttribute DrinkEditBindingModel drinkEditBindingModel) {

        menuService.editDrink(modelMapper.map(drinkEditBindingModel, DrinkServiceModel.class));

        return redirect("/items/drinks/all");
    }

    @GetMapping("/ingredients/all")
    public ModelAndView allIngredients() {
        AllIngredientsViewModel allIngredients = modelMapper.map(
                ingredientService.getAllIngredients(),
                AllIngredientsViewModel.class
        );

        return view("items/ingredients-all", "ingredients", allIngredients);
    }

    @GetMapping("/ingredients/edit/{id}")
    public ModelAndView editIngredient(@PathVariable String id) {
        IngredientViewModel drinkViewModel = modelMapper.map(
                ingredientService.getIngredientById(id),
                IngredientViewModel.class
        );

        return view("items/ingredient-edit", "ingredient", drinkViewModel);
    }

    @PostMapping("/ingredients/edit/{id}")
    public ModelAndView editIngredientConfirm(@PathVariable String id,
                                              @ModelAttribute IngredientBindingModel ingredientBindingModel) {

        ingredientService.editIngredient(id, modelMapper.map(ingredientBindingModel, IngredientServiceModel.class));

        return redirect("/items/ingredients/all");
    }

    @GetMapping("/pizzas/all")
    public ModelAndView allPizzas() {
        List<PizzaViewModel> pizzas = menuService.getAllPizzasOrderedByName().stream()
                .map(pizza -> modelMapper.map(pizza, PizzaViewModel.class))
                .collect(Collectors.toList());

        return view("items/pizzas-all", "pizzas", pizzas);
    }
}
