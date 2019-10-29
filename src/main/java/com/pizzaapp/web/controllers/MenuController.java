package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.menu.DrinkBindingModel;
import com.pizzaapp.domain.models.service.menu.DrinkServiceModel;
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

    private final MenuService menuService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuController(MenuService menuService,
                          CloudinaryService cloudinaryService,
                          ModelMapper modelMapper) {
        this.menuService = menuService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
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
