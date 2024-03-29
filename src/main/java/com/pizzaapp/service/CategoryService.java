package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    void addCategory(CategoryServiceModel categoryServiceModel);

    CategoryServiceModel getCategoryById(String categoryId);

    List<CategoryServiceModel> getAllCategories();
}
