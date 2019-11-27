package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.menu.DrinkBindingModel;
import com.pizzaapp.domain.models.binding.menu.PizzaAddBingingModel;
import com.pizzaapp.domain.models.service.ingredients.AllIngredientsServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaAddServiceModel;
import com.pizzaapp.domain.models.service.menu.PizzaServiceModel;
import com.pizzaapp.domain.models.view.ingredients.AllIngredientsViewModel;
import com.pizzaapp.domain.models.view.menu.DrinkViewModel;
import com.pizzaapp.domain.models.view.menu.PizzaViewModel;
import com.pizzaapp.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/menu")
@PreAuthorize("hasRole('ROLE_MODERATOR')")
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final IngredientService ingredientService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuController(MenuService menuService,
                          IngredientService ingredientService,
                          CloudinaryService cloudinaryService,
                          ModelMapper modelMapper) {
        this.menuService = menuService;
        this.ingredientService = ingredientService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    private String addPictureToCloud(MultipartFile largeImgUrl) {
        return cloudinaryService.uploadImage(largeImgUrl);
    }

    @GetMapping("/drinks/add")
    public ModelAndView addDrink() {
        return view("menu/drink-add");
    }

    @PostMapping("/drinks/add")
    public ModelAndView addDrinkConfirm(@ModelAttribute DrinkBindingModel drinkBindingModel) {
        DrinkServiceModel drinkServiceModel = modelMapper.map(drinkBindingModel, DrinkServiceModel.class);

        String uploadImageUrl = addPictureToCloud(drinkBindingModel.getImage());

        drinkServiceModel.setImageUrl(uploadImageUrl);

        menuService.addDrink(drinkServiceModel);

        return redirect("/");
    }

    @GetMapping("/pizzas/add")
    public ModelAndView addPizza() {
        AllIngredientsServiceModel IngredientsServiceModel = ingredientService.getAllIngredients();

        AllIngredientsViewModel allIngredients = modelMapper.map(IngredientsServiceModel, AllIngredientsViewModel.class);

        return view("menu/pizza-add", "allIngredients", allIngredients);
    }

    @PostMapping("/pizzas/add")
    public ModelAndView addPizzaConfirm(@ModelAttribute PizzaAddBingingModel pizzaAddBingingModel) {
        PizzaAddServiceModel pizzaAddServiceModel = modelMapper.map(pizzaAddBingingModel, PizzaAddServiceModel.class);

        String uploadImageUrl = addPictureToCloud(pizzaAddBingingModel.getImageUrl());

        pizzaAddServiceModel.setImageUrl(uploadImageUrl);

        menuService.addPizza(pizzaAddServiceModel);

        return redirect("/");
    }

    @GetMapping("/order/pizza")
    @PreAuthorize("hasRole('ROLE_USER') && !hasRole('ROLE_COURIER')")
    public ModelAndView orderPizza() {
        List<PizzaViewModel> allPizzas = menuService.getAllPizzasOrderedByName().stream()
                .map(pizza -> modelMapper.map(pizza, PizzaViewModel.class))
                .collect(Collectors.toList());

        return view("menu/order/order-pizza", "allPizzas", allPizzas);
    }

    @GetMapping("/order/pizza/{id}")
    @PreAuthorize("hasRole('ROLE_USER') && !hasRole('ROLE_COURIER')")
    public ModelAndView orderPizzaDetails(@PathVariable String id) {
        PizzaServiceModel pizzaServiceModel = menuService.getPizzaById(id);

        PizzaViewModel pizzaById = modelMapper.map(pizzaServiceModel, PizzaViewModel.class);

        return view("menu/order/order-pizza-details", "pizza", pizzaById);
    }

    @GetMapping("/order/drink")
    @PreAuthorize("hasRole('ROLE_USER') && !hasRole('ROLE_COURIER')")
    public ModelAndView orderDrink() {
        List<DrinkViewModel> allDrinks = menuService.getAllDrinksOrderedByName().stream()
                .map(drink -> modelMapper.map(drink, DrinkViewModel.class))
                .collect(Collectors.toList());

        return view("menu/order/order-drink", "allDrinks", allDrinks);
    }

    @GetMapping("/order/drink/{id}")
    @PreAuthorize("hasRole('ROLE_USER') && !hasRole('ROLE_COURIER')")
    public ModelAndView orderDrinkDetails(@PathVariable String id) {
        DrinkServiceModel drinkServiceModel = menuService.getDrinkById(id);

        DrinkViewModel drinkById = modelMapper.map(drinkServiceModel, DrinkViewModel.class);

        return view("menu/order/order-drink-details", "drink", drinkById);
    }

}
