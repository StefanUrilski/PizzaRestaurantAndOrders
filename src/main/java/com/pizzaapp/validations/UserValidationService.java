package com.pizzaapp.validations;

import com.pizzaapp.domain.models.service.UserServiceModel;

public interface UserValidationService {

    String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    boolean isValid(UserServiceModel userServiceModel);

}
