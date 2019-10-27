package com.pizzaapp.repository.menu;

import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    @Query("" +
            "select i " +
            "from Ingredient as i " +
            "join i.category as c " +
            "where c.name = :category and i.name = :name")
    Optional<Ingredient> findByCategoryAndName(String category, String name);

    @Query("" +
            "select i " +
            "from Ingredient as i " +
            "join i.category as c " +
            "where c.name = :category")
    List<Ingredient> findAllByCategoryOrderByNameAsc(String category);
}
