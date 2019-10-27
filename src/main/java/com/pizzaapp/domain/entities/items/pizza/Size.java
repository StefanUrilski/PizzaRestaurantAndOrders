package com.pizzaapp.domain.entities.items.pizza;

import com.pizzaapp.domain.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    private String size;
    private String numberOfSlices;
    private String quantity;

    @Column(name = "size", nullable = false, unique = true)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "number_of_slices", nullable = false)
    public String getNumberOfSlices() {
        return numberOfSlices;
    }

    public void setNumberOfSlices(String numberOfSlices) {
        this.numberOfSlices = numberOfSlices;
    }

    @Column(name = "quantity", nullable = false)
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
