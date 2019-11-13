package com.pizzaapp.domain.models.service.ingredients;

import java.util.List;

public class AllIngredientsServiceModel {

    private List<IngredientServiceModel> meats;
    private List<IngredientServiceModel> cheeses;
    private List<IngredientServiceModel> vegetables;

    public AllIngredientsServiceModel() {
    }

    public AllIngredientsServiceModel(
            List<IngredientServiceModel> meats,
            List<IngredientServiceModel> cheeses,
            List<IngredientServiceModel> vegetables
    ) {
        this.meats = meats;
        this.cheeses = cheeses;
        this.vegetables = vegetables;
    }

    public List<IngredientServiceModel> getMeats() {
        return meats;
    }

    public void setMeats(List<IngredientServiceModel> meats) {
        this.meats = meats;
    }

    public List<IngredientServiceModel> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<IngredientServiceModel> cheeses) {
        this.cheeses = cheeses;
    }

    public List<IngredientServiceModel> getVegetables() {
        return vegetables;
    }

    public void setVegetables(List<IngredientServiceModel> vegetables) {
        this.vegetables = vegetables;
    }
}
