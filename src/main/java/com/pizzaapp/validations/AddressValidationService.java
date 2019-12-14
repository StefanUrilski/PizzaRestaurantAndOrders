package com.pizzaapp.validations;

import com.pizzaapp.domain.models.service.AddressServiceModel;

public interface AddressValidationService {

    String PHONE_PATTERN = "^[+]*[0-9]{10,12}$";

    boolean isValid(AddressServiceModel addressServiceModel);

}
