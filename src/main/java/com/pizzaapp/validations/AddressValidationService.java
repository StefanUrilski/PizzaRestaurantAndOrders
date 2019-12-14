package com.pizzaapp.validations;

import com.pizzaapp.domain.models.service.AddressServiceModel;

public interface AddressValidationService {

    boolean isValid(AddressServiceModel addressServiceModel);

}
