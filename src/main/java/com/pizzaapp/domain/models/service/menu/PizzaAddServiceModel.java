package com.pizzaapp.domain.models.service.menu;

import java.util.List;

public class PizzaAddServiceModel {

    private String name;
    private List<String> ingredientsIds;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
