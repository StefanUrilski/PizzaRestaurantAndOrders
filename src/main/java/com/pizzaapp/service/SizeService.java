package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;

public interface SizeService {

    void addSize(SizeServiceModel sizeServiceModel);

    SizeServiceModel getBySizeName(String sizeName);
}
