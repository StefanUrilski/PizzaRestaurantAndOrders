package com.pizzaapp.domain.entities.items;

import com.pizzaapp.domain.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class MenuItem extends BaseEntity {

    private String name;
    private BigDecimal price;
    private String imageUrl;

    public MenuItem() {
    }

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
