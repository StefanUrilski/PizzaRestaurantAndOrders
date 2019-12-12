package com.pizzaapp.service;

import com.pizzaapp.domain.models.service.AddressServiceModel;

import java.util.List;

public interface AddressService {

    boolean addAddress(AddressServiceModel addressServiceModel);

    AddressServiceModel getAddressById(String id);

    AddressServiceModel editAddress(AddressServiceModel addressServiceModel);

    List<AddressServiceModel> getUserAddressesOrderedByTown(String email);
}
