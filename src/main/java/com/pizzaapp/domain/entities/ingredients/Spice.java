package com.pizzaapp.domain.entities.ingredients;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "spices")
public class Spice extends Ingredient {

    public Spice() {
    }
}
