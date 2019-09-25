package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Non-existent name.")
public class NameNotFoundException extends RuntimeException {

    public NameNotFoundException(String message) {
        super(message);
    }
}
