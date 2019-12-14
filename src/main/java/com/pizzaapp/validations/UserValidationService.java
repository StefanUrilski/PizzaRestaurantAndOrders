package com.pizzaapp.validations;

import com.pizzaapp.domain.models.service.UserServiceModel;

public interface UserValidationService {

    boolean isValid(UserServiceModel userServiceModel);

}
