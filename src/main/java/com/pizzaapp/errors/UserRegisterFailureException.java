package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error occurred during user registration.")
public class UserRegisterFailureException extends RuntimeException {

    public UserRegisterFailureException(String message) {
        super(message);
    }
}
