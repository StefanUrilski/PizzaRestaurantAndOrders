package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.errors.IdNotFoundException;
import com.pizzaapp.repository.AddressRepository;
import com.pizzaapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(UserRepository userRepository,
                              AddressRepository addressRepository,
                              ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addAddress(AddressServiceModel addressServiceModel) {
        this.addressRepository.save(modelMapper.map(addressServiceModel, Address.class));

        return true;
    }

    @Override
    public AddressServiceModel getAddressById(String id) {
        Address addressEntity = addressRepository.findById(id).orElse(null);

        checkAddressExistence(addressEntity);

        return modelMapper.map(addressEntity, AddressServiceModel.class);
    }

    @Override
    public boolean editAddress(AddressServiceModel addressServiceModel) {
        Address addressEntity = addressRepository.findById(addressServiceModel.getId()).orElse(null);

        checkAddressExistence(addressEntity);

        addressEntity = modelMapper.map(addressServiceModel, Address.class);

        addressRepository.save(addressEntity);

        return true;
    }

    @Override
    public List<AddressServiceModel> getUserAddressesOrderedByTown(String email) {
        return addressRepository.findAllUserAddressesOrderedByName(email)
                .stream()
                .map(address -> modelMapper.map(address, AddressServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAddress(String id, String name) {
        Address address = addressRepository.findById(id).orElse(null);

        checkAddressExistence(address);

        addressRepository.deleteById(id);
    }

    private void checkAddressExistence(Address addressEntity) {
        if (addressEntity == null) {
            throw new IdNotFoundException(Constants.WRONG_NON_EXISTENT_ID);
        }
    }
}
