package com.pizzaapp.domain.models.binding.menu;

public class SizeBindingModel {

    private String size;
    private String numberOfSlices;
    private Integer quantity;
    private double multiplier;

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNumberOfSlices() {
        return numberOfSlices;
    }

    public void setNumberOfSlices(String numberOfSlices) {
        this.numberOfSlices = numberOfSlices;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}

