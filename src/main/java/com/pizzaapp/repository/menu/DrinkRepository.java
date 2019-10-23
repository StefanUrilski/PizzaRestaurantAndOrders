package com.pizzaapp.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pizzaapp.domain.entities.items.Drink;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, String> {

    Optional<Drink> findByName(String name);

    @Query("SELECT d FROM Drink d ORDER BY d.name")
    List<Drink> findAllOrderedAlphabetically();
}
