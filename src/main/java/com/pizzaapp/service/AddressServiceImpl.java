package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.errors.IdNotFoundException;
import com.pizzaapp.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addAddress(AddressServiceModel addressServiceModel) {
        this.addressRepository.save(this.modelMapper.map(addressServiceModel, Address.class));

        return true;
    }

    @Override
    public AddressServiceModel getAddressById(String id) {
        Address addressEntity = this.addressRepository.findById(id).orElse(null);

        this.checkAddressExistence(addressEntity);

        return this.modelMapper.map(addressEntity, AddressServiceModel.class);
    }

    @Override
    public boolean editAddress(AddressServiceModel addressServiceModel) {
        Address addressEntity = this.addressRepository.findById(addressServiceModel.getId()).orElse(null);

        this.checkAddressExistence(addressEntity);

        addressEntity = this.modelMapper.map(addressServiceModel, Address.class);

        this.addressRepository.save(addressEntity);

        return true;
    }

    @Override
    public List<AddressServiceModel> getUserAddressesOrderedByName(String email) {
        return this.addressRepository.findAllUserAddressesOrderedByName(email)
                .stream()
                .map(address -> this.modelMapper.map(address, AddressServiceModel.class))
                .collect(Collectors.toList());
    }

    private void checkAddressExistence(Address addressEntity) {
        if (addressEntity == null) {
            throw new IdNotFoundException(Constants.WRONG_NON_EXISTENT_ID);
        }
    }
}
