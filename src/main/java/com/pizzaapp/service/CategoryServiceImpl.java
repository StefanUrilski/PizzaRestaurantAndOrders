package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.repository.menu.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel getCategoryByName(String name) {
        return null;
    }

    @Override
    public List<CategoryServiceModel> getAllCategories() {
        return null;
    }
}
