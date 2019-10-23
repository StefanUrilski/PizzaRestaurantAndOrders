package com.pizzaapp.web.controllers;

import com.pizzaapp.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ingredients")
@PreAuthorize("hasRole('ROLE_MODERATOR')")
public class IngredientsController extends BaseController {

    private final ModelMapper modelMapper;
    private final IngredientService ingredientService;

    @Autowired
    public IngredientsController(ModelMapper modelMapper,
                                 IngredientService ingredientService) {
        this.modelMapper = modelMapper;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/category/add")
    public ModelAndView addCategory() {
        return view("menu/category-add");
    }

}
