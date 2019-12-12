package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.Town;
import com.pizzaapp.domain.entities.items.pizza.Category;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.domain.models.service.ingredients.CategoryServiceModel;
import com.pizzaapp.repository.menu.CategoryRepository;
import com.pizzaapp.service.CategoryService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTests extends TestBase {

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService service;

    @Test
    public void addCategory_shouldAddCategory() {
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("categoryName");

        when(categoryRepository.findByName("categoryName"))
                .thenReturn(Optional.empty());

        when(categoryRepository.saveAndFlush(any()))
                .thenReturn(new Category());

        service.addCategory(category);
        verify(categoryRepository)
                .saveAndFlush(any());
    }

    @Test
    public void getCategoryById_whenIdExist_shouldReturnSameCategory(){
        Category category = new Category();
        category.setName("CategoryName");

        Mockito.when(categoryRepository.findById("1"))
                .thenReturn(Optional.of(category));

        CategoryServiceModel categoryService = service.getCategoryById("1");

        assertEquals(category.getName(), categoryService.getName());
    }

    @Test
    public void getAllCategories_shouldReturnAllCategories() {
        Category c1 = new Category();
        Category c2 = new Category();

        c1.setName("NewOne");
        c2.setName("DifferentOne");

        List<Category> actual = new ArrayList<>();
        actual.add(c1);
        actual.add(c2);

        Mockito.when(categoryRepository.findAll())
                .thenReturn(actual);

        List<CategoryServiceModel> expected = service.getAllCategories();

        assertEquals(actual.size(), expected.size());
    }
}
