package com.pizzaapp.domain.models.service.ingredients;

import java.math.BigDecimal;

public class DoughServiceModel {

    private String id;
    private String name;
    private BigDecimal price;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}