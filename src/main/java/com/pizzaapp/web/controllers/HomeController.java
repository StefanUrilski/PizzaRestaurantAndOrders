package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.view.menu.PizzaHomeViewModel;
import com.pizzaapp.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private static List<PizzaHomeViewModel> pizzas;

    private final MenuService menuService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(MenuService menuService, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView model) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::mixPizzas, 0, 30, TimeUnit.MINUTES);
//        executorService.scheduleAtFixedRate(this::mixPizzas, 0, 10, TimeUnit.SECONDS);

        if (pizzas == null) {
            mixPizzas();
        }

        model.addObject("view", "index");
        model.addObject("firstPizza", pizzas.get(0));
        model.addObject("secondPizza", pizzas.get(1));
        model.addObject("thirdPizza", pizzas.get(2));

        model.setViewName("fragments/base-layout");

        return model;
    }

    private void mixPizzas() {
        pizzas = menuService.getAllPizzasWithLargeImage().stream()
                .map(pizza -> modelMapper.map(pizza, PizzaHomeViewModel.class))
                .collect(Collectors.toList());

        Collections.shuffle(pizzas);
    }
}
