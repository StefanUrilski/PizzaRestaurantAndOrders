package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.menu.SizeBindingModel;
import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.domain.models.view.ingredients.CategoryViewModel;
import com.pizzaapp.domain.models.binding.menu.IngredientBindingModel;
import com.pizzaapp.service.CategoryService;
import com.pizzaapp.service.IngredientService;
import com.pizzaapp.service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ingredients")
public class IngredientController extends BaseController {

    private final SizeService sizeService;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientController(SizeService sizeService,
                                CategoryService categoryService,
                                IngredientService ingredientService,
                                ModelMapper modelMapper) {
        this.sizeService = sizeService;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
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

    @GetMapping("/add")
    public ModelAndView addIngredient() {
        return view("menu/ingredient-add");
    }

    @PostMapping("/add")
    public ModelAndView addIngredientConfirm(@ModelAttribute IngredientBindingModel ingredientBindingModel) {
        ingredientService.addIngredient(modelMapper.map(ingredientBindingModel, IngredientServiceModel.class));

        return redirect("/");
    }

}
