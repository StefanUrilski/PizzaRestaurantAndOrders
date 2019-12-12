package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.items.pizza.Category;
import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.errors.ItemAddFailureException;
import com.pizzaapp.repository.menu.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = categoryRepository.findByName(categoryServiceModel.getName()).orElse(null);

        ExistService.checkIfItemExistThrowException(category, categoryServiceModel.getName());

        category = modelMapper.map(categoryServiceModel, Category.class);

        try {
            categoryRepository.saveAndFlush(category);
        } catch (Exception ex) {
            throw new ItemAddFailureException(Constants.ITEM_ADD_EXCEPTION);
        }
    }

    @Override
    public CategoryServiceModel getCategoryById(String id) {
        Category category = categoryRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(category);

        return modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }
}
