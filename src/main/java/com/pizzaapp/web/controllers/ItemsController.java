package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.view.menu.DrinkViewModel;
import com.pizzaapp.service.IngredientService;
import com.pizzaapp.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView addDrink() {
        List<DrinkViewModel> drinks = menuService.getDrinksOrderedByName().stream()
                .map(drink -> modelMapper.map(drink, DrinkViewModel.class))
                .collect(Collectors.toList());

        return view("items/drinks-all", "drinks", drinks);
    }
}
