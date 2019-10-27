package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.ingredients.SizeBindingModel;
import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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


}
