package com.pizzaapp.domain.models.service.menu;

import com.pizzaapp.domain.models.service.ingredients.*;

import java.math.BigDecimal;
import java.util.List;

public class PizzaServiceModel {

    private String id;
    private String name;
    private BigDecimal price;
    private String description;
//    private DoughServiceModel dough;
    private List<CheeseServiceModel> cheeses;
    private List<MeatServiceModel> meats;
    private List<VegetableServiceModel> vegetables;
    private String imageUrl;

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

//    public DoughServiceModel getDough() {
//        return this.dough;
//    }
//
//    public void setDough(DoughServiceModel dough) {
//        this.dough = dough;
//    }

    public List<CheeseServiceModel> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(List<CheeseServiceModel> cheeses) {
        this.cheeses = cheeses;
    }

    public List<MeatServiceModel> getMeats() {
        return this.meats;
    }

    public void setMeats(List<MeatServiceModel> meats) {
        this.meats = meats;
    }

    public List<VegetableServiceModel> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(List<VegetableServiceModel> vegetables) {
        this.vegetables = vegetables;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
