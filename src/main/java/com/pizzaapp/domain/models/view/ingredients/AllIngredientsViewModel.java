package com.pizzaapp.domain.models.view.ingredients;

import com.pizzaapp.domain.models.service.ingredients.CheeseServiceModel;
import com.pizzaapp.domain.models.service.ingredients.MeatServiceModel;
import com.pizzaapp.domain.models.service.ingredients.VegetableServiceModel;

import java.util.List;

public class AllIngredientsViewModel {

    private List<MeatServiceModel> meats;
    private List<CheeseServiceModel> cheeses;
    private List<VegetableServiceModel> vegetables;

    public AllIngredientsViewModel() {
    }

    public AllIngredientsViewModel(
            List<MeatServiceModel> meats,
            List<CheeseServiceModel> cheeses,
            List<VegetableServiceModel> vegetables
    ) {
        this.meats = meats;
        this.cheeses = cheeses;
        this.vegetables = vegetables;
    }

    public List<MeatServiceModel> getMeats() {
        return meats;
    }

    public void setMeats(List<MeatServiceModel> meats) {
        this.meats = meats;
    }

    public List<CheeseServiceModel> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<CheeseServiceModel> cheeses) {
        this.cheeses = cheeses;
    }

    public List<VegetableServiceModel> getVegetables() {
        return vegetables;
    }

    public void setVegetables(List<VegetableServiceModel> vegetables) {
        this.vegetables = vegetables;
    }
}
