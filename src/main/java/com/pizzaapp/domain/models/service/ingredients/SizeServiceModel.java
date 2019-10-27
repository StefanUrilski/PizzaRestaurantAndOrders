package com.pizzaapp.domain.models.service.ingredients;

public class SizeServiceModel {

    private String id;
    private String size;
    private String numberOfSlices;
    private Integer quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
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
