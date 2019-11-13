package com.pizzaapp.domain.models.view.ingredients;

import java.util.List;

public class AllIngredientsViewModel {

    private List<IngredientViewModel> meats;
    private List<IngredientViewModel> cheeses;
    private List<IngredientViewModel> vegetables;

    public AllIngredientsViewModel() {
    }

    public AllIngredientsViewModel(
            List<IngredientViewModel> meats,
            List<IngredientViewModel> cheeses,
            List<IngredientViewModel> vegetables
    ) {
        this.meats = meats;
        this.cheeses = cheeses;
        this.vegetables = vegetables;
    }

    public List<IngredientViewModel> getMeats() {
        return meats;
    }

    public void setMeats(List<IngredientViewModel> meats) {
        this.meats = meats;
    }

    public List<IngredientViewModel> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<IngredientViewModel> cheeses) {
        this.cheeses = cheeses;
    }

    public List<IngredientViewModel> getVegetables() {
        return vegetables;
    }

    public void setVegetables(List<IngredientViewModel> vegetables) {
        this.vegetables = vegetables;
    }
}
