package com.pizzaapp.domain.models.binding.menu;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PizzaAddBingingModel {

    private String name;
    private List<String> ingredientsIds;
    private MultipartFile imageUrl;
    private MultipartFile largeImgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredientsIds() {
        return ingredientsIds;
    }

    public void setIngredientsIds(List<String> ingredientsIds) {
        this.ingredientsIds = ingredientsIds;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getLargeImgUrl() {
        return largeImgUrl;
    }

    public void setLargeImgUrl(MultipartFile largeImgUrl) {
        this.largeImgUrl = largeImgUrl;
    }
}
