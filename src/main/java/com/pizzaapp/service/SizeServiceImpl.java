package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.items.pizza.Size;
import com.pizzaapp.domain.models.service.ingredients.SizeServiceModel;
import com.pizzaapp.errors.ItemAddFailureException;
import com.pizzaapp.errors.ItemAlreadyExistsException;
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

    @Override
    public void addSize(SizeServiceModel sizeServiceModel) {

        Size size = sizeRepository.findBySize(sizeServiceModel.getName()).orElse(null);

        ExistService.checkIfItemNotExistThrowException(size, sizeServiceModel.getName());

        size = modelMapper.map(sizeServiceModel, Size.class);

        try {
            sizeRepository.save(size);
        } catch (Exception ex) {
            throw new ItemAddFailureException(Constants.ITEM_ADD_EXCEPTION);
        }
    }

    @Override
    public SizeServiceModel getBySizeName(String sizeName) {
        Size size = sizeRepository.findBySize(sizeName).orElse(null);

        ExistService.checkIfItemExistThrowException(size);

        return modelMapper.map(size, SizeServiceModel.class);
    }
}
