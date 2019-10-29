package com.pizzaapp.web.controllers;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.models.binding.ingredients.SizeBindingModel;
import com.pizzaapp.domain.models.binding.menu.DrinkBindingModel;
import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
import com.pizzaapp.domain.models.view.CategoryViewModel;
import com.pizzaapp.domain.models.view.IngredientBindingModel;
import com.pizzaapp.errors.ItemAddFailureException;
import com.pizzaapp.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/menu")
@PreAuthorize("hasRole('ROLE_MODERATOR')")
public class MenuController extends BaseController {

    private final SizeService sizeService;
    private final CategoryService categoryService;
    private final MenuService menuService;
    private final IngredientService ingredientService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuController(SizeService sizeService,
                          CategoryService categoryService,
                          MenuService menuService,
                          IngredientService ingredientService,
                          CloudinaryService cloudinaryService,
                          ModelMapper modelMapper) {
        this.sizeService = sizeService;
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.ingredientService = ingredientService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/size/add")
    public ModelAndView pizzaSize() {
        return view("menu/basics/size-add");
    }

    @PostMapping("/size/add")
    public ModelAndView pizzaSizeConfirm(@ModelAttribute SizeBindingModel sizeBindingModel) {
        sizeService.addSize(modelMapper.map(sizeBindingModel, SizeServiceModel.class));

        return redirect("/");
    }

    @GetMapping("/category/add")
    public ModelAndView addCategory() {
        return view("menu/basics/category-add");
    }

    @PostMapping("/category/add")
    public ModelAndView addCategoryConfirm(@ModelAttribute CategoryServiceModel categoryServiceModel) {
        categoryService.addCategory(modelMapper.map(categoryServiceModel, CategoryServiceModel.class));

        return redirect("/");
    }

    @GetMapping("/category/fetch")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/ingredients/add")
    public ModelAndView addIngredient() {
        return view("menu/ingredient-add");
    }

    @PostMapping("/ingredients/add")
    public ModelAndView addIngredientConfirm(@ModelAttribute IngredientBindingModel ingredientBindingModel) {
        ingredientService.addIngredient(modelMapper.map(ingredientBindingModel, IngredientServiceModel.class));

        return redirect("/");
    }

    @GetMapping("/drinks/add")
    public ModelAndView addDrink() {
        return view("menu/drink-add");
    }

    @PostMapping("/drinks/add")
    public ModelAndView addDrinkConfirm(@ModelAttribute DrinkBindingModel drinkBindingModel) {
        DrinkServiceModel drinkServiceModel = modelMapper.map(drinkBindingModel, DrinkServiceModel.class);

        String uploadImageUrl = cloudinaryService.uploadImage(drinkBindingModel.getImage());

        drinkServiceModel.setImageUrl(uploadImageUrl);

        menuService.addDrink(drinkServiceModel);

        return redirect("/");
    }
}
