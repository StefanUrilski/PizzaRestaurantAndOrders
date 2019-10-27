package com.pizzaapp.domain.models.binding.ingredients;

public class SizeBindingModel {

    private String size;
    private String numberOfSlices;
    private Integer quantity;

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
}

