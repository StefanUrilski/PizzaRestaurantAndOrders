package com.pizzaapp.domain.models.service.ingredients;

import com.pizzaapp.domain.models.service.ingredients.*;

import java.util.List;

public class AllIngredientsServiceModel {

    private List<MeatServiceModel> meats;
    private List<SizeServiceModel> sizes;
    private List<DoughServiceModel> doughs;
    private List<CheeseServiceModel> cheeses;
    private List<VegetableServiceModel> vegetables;

    public AllIngredientsServiceModel() {
    }

    public AllIngredientsServiceModel(
            List<MeatServiceModel> meats,
            List<SizeServiceModel> sizes,
            List<DoughServiceModel> doughs,
            List<CheeseServiceModel> cheeses,
            List<VegetableServiceModel> vegetables
    ) {
        this.meats = meats;
        this.sizes = sizes;
        this.doughs = doughs;
        this.cheeses = cheeses;
        this.vegetables = vegetables;
    }

    public List<MeatServiceModel> getMeats() {
        return meats;
    }

    public void setMeats(List<MeatServiceModel> meats) {
        this.meats = meats;
    }

    public List<SizeServiceModel> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeServiceModel> sizes) {
        this.sizes = sizes;
    }

    public List<DoughServiceModel> getDoughs() {
        return doughs;
    }

    public void setDoughs(List<DoughServiceModel> doughs) {
        this.doughs = doughs;
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
