package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;

import java.util.Arrays;
import java.util.List;

public interface SizeService {

    void addSize(SizeServiceModel sizeServiceModel);

    SizeServiceModel getBySizeName(String sizeName);

    List<SizeServiceModel> getAllSizes();
}
