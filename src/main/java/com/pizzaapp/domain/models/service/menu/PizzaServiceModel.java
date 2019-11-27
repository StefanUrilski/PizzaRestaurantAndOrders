package com.pizzaapp.domain.models.service.menu;

import com.pizzaapp.domain.models.service.ingredients.*;

import java.math.BigDecimal;
import java.util.List;

public class PizzaServiceModel {

    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private String dough;
    private List<IngredientServiceModel> ingredients;
    private SizeServiceModel size;
    private String imageUrl;
    private String largeImgUrl;

    public PizzaServiceModel() {
        this.price = new BigDecimal(0);
    }

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
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public List<IngredientServiceModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientServiceModel> ingredients) {
        this.ingredients = ingredients;
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

    public String getLargeImgUrl() {
        return largeImgUrl;
    }

    public void setLargeImgUrl(String largeImgUrl) {
        this.largeImgUrl = largeImgUrl;
    }
}
