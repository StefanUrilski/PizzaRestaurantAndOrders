package com.pizzaapp.domain.entities.ingredients;

import com.pizzaapp.domain.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    private String size;
    private BigDecimal price;
    private Integer numberOfSlices;

    public Size() {
    }

    @Column(name = "size", nullable = false, unique = true, updatable = false)
    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "number_of_slices", nullable = false, unique = true, updatable = false)
    public Integer getNumberOfSlices() {
        return this.numberOfSlices;
    }

    public void setNumberOfSlices(Integer numberOfSlices) {
        this.numberOfSlices = numberOfSlices;
    }
}
