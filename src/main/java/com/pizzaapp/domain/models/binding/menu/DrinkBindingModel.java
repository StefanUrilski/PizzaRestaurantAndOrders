package com.pizzaapp.domain.models.binding.menu;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class DrinkBindingModel {

    private String name;
    private BigDecimal price;
    private MultipartFile image;
    private boolean isAlcoholic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }
}
