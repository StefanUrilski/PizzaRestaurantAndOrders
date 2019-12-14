package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.errors.AddressIncompleteException;
import com.pizzaapp.repository.AddressRepository;
import com.pizzaapp.validations.AddressValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressValidationService validationService;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository,
                              AddressValidationService validationService,
                              ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.validationService = validationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addAddress(AddressServiceModel addressServiceModel) {
        if (validationService.isValid(addressServiceModel)) {
            throw new AddressIncompleteException(Constants.WRONG_ADDRESS);
        }

        addressRepository.save(modelMapper.map(addressServiceModel, Address.class));

        return true;
    }

    @Override
    public AddressServiceModel getAddressById(String id) {
        Address address = addressRepository.findById(id).orElse(null);

        ExistService.checkIfItemNotExistThrowException(address);

        return modelMapper.map(address, AddressServiceModel.class);
    }

    @Override
    public AddressServiceModel editAddress(AddressServiceModel addressServiceModel) {
        if (validationService.isValid(addressServiceModel)) {
            throw new AddressIncompleteException(Constants.WRONG_ADDRESS);
        }

        Address address = addressRepository.findById(addressServiceModel.getId()).orElse(null);

        ExistService.checkIfItemNotExistThrowException(address);

        address = modelMapper.map(addressServiceModel, Address.class);

        addressRepository.save(address);

        return modelMapper.map(address, AddressServiceModel.class);
    }

    @Override
    public List<AddressServiceModel> getUserAddressesOrderedByTown(String email) {
        return addressRepository.findAllUserAddressesOrderedByName(email)
                .stream()
                .map(address -> modelMapper.map(address, AddressServiceModel.class))
                .collect(Collectors.toList());
    }
}
