package com.pizzaapp.domain.models.view.cart;

import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;

import java.math.BigDecimal;

public class PizzaOrderViewModel {

    private String id;
    private String name;
    private BigDecimal price;
    private String dough;
    private SizeServiceModel size;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public SizeServiceModel getSize() {
        return size;
    }

    public void setSize(SizeServiceModel size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
