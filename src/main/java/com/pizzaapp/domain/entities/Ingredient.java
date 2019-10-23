package com.pizzaapp.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Category category;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JoinTable(name = "ingredients_categories",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
