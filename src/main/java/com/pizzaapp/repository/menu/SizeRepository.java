package com.pizzaapp.repository.menu;

import com.pizzaapp.domain.entities.items.pizza.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, String> {

    Size getBySize(String size);
}
