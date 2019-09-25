package com.pizzaapp.domain.entities.ingredients;

import com.pizzaapp.domain.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Ingredient extends BaseEntity {

    private String name;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
