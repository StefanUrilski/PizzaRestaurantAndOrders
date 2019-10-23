package com.pizzaapp.domain.models.binding.ingredients;

import java.math.BigDecimal;

public class VegetableBindingModel {

    private String name;
    private BigDecimal price;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
