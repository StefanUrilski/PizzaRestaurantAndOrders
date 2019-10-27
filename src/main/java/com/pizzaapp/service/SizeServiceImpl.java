package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.items.pizza.Ingredient;
import com.pizzaapp.domain.entities.items.pizza.Size;
import com.pizzaapp.domain.models.service.ingredients.IngredientServiceModel;
import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.errors.ItemAlreadyExistsException;
import com.pizzaapp.errors.NameNotFoundException;
import com.pizzaapp.repository.menu.SizeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SizeServiceImpl(SizeRepository sizeRepository,
                           ModelMapper modelMapper) {
        this.sizeRepository = sizeRepository;
        this.modelMapper = modelMapper;
    }

    private void checkIfExist(Object entity, String name) {
        if (entity != null) {
            throw new ItemAlreadyExistsException(String.format(Constants.ITEM_ALREADY_EXISTS, name));
        }
    }

    private void checkIfExist(Object entity) {
        if (entity == null) {
            throw new NameNotFoundException(Constants.WRONG_NON_EXISTENT_NAME);
        }
    }

    @Override
    public void addSize(SizeServiceModel sizeServiceModel) {

        Size size = sizeRepository.findBySize(sizeServiceModel.getName()).orElse(null);

        checkIfExist(size, sizeServiceModel.getName());

        size = modelMapper.map(sizeServiceModel, Size.class);

        try {
            sizeRepository.save(size);
        } catch (Exception ex) {
            throw new ItemAlreadyExistsException(Constants.ITEM_ADD_EXCEPTION);
        }
    }

    @Override
    public SizeServiceModel getBySizeName(String sizeName) {
        Size size = sizeRepository.findBySize(sizeName).orElse(null);

        checkIfExist(size);

        return modelMapper.map(size, SizeServiceModel.class);
    }
}
