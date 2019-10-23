package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel getCategoryByName(String name);

    List<CategoryServiceModel> getAllCategories();

}
