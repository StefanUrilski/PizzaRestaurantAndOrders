package com.pizzaapp.repository.menu;

import com.pizzaapp.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}
