package com.pizzaapp.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pizzaapp.domain.entities.items.Pizza;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, String> {

    Optional<Pizza> findByName(String name);

    @Query("SELECT p FROM Pizza p ORDER BY p.name")
    List<Pizza> findAllOrderedAlphabetically();
}
