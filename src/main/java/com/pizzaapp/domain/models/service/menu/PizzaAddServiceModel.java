package com.pizzaapp.domain.models.service.menu;

import com.pizzaapp.domain.entities.items.pizza.Dough;

import java.util.List;

public class PizzaAddServiceModel {

    private String name;
    private List<String> ingredientsIds;
    private String imageUrl;
    private String dough;
    private String largeImgUrl;

    public PizzaAddServiceModel() {
        this.dough = Dough.Traditional.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredientsIds() {
        return ingredientsIds;
    }

    public void setIngredientsIds(List<String> ingredientsIds) {
        this.ingredientsIds = ingredientsIds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public String getLargeImgUrl() {
        return largeImgUrl;
    }

    public void setLargeImgUrl(String largeImgUrl) {
        this.largeImgUrl = largeImgUrl;
    }
}
