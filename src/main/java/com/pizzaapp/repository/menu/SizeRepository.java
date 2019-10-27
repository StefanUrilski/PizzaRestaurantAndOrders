package com.pizzaapp.repository.menu;

import com.pizzaapp.domain.entities.items.pizza.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, String> {

    Optional<Size> findBySize(String size);
}
