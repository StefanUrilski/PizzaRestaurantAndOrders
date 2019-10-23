package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.ingredients.*;
import com.pizzaapp.domain.models.service.ingredients.*;
import com.pizzaapp.service.IngredientService;
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
@RequestMapping("menu/ingredients/add")
public class IngredientsController extends BaseController {

    private final ModelMapper modelMapper;
    private final IngredientService ingredientService;

    @Autowired
    public IngredientsController(ModelMapper modelMapper,
                                 IngredientService ingredientService) {
        this.modelMapper = modelMapper;
        this.ingredientService = ingredientService;
    }



}
