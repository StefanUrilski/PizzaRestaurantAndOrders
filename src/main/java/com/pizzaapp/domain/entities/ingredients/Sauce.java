package com.pizzaapp.domain.entities.ingredients;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sauces")
public class Sauce extends Ingredient {

    public Sauce() {
    }
}
