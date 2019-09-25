package com.pizzaapp.domain.entities.ingredients;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cheeses")
public class Cheese extends Ingredient {

    private BigDecimal price;

    public Cheese() {
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
