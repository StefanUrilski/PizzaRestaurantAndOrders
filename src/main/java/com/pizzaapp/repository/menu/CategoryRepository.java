package com.pizzaapp.repository.menu;

import com.pizzaapp.domain.entities.items.pizza.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByName(String name);
}
