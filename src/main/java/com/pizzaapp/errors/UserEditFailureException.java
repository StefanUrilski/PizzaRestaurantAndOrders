package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error occurred during user profile edit.")
public class UserEditFailureException extends RuntimeException {

    public UserEditFailureException(String message) {
        super(message);
    }
}
