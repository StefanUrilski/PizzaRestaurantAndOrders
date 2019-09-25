package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Non-existent id.")
public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String message) {
        super(message);
    }
}
