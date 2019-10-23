package com.pizzaapp.repository;

import com.pizzaapp.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    Optional<Ingredient> findByCategoryAndName(String category, String name);

    List<Ingredient> findAllByCategoryOrderByNameAsc(String category);
}
