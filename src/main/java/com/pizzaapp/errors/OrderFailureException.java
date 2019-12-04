package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Error occurred during order add.")
public class OrderFailureException extends RuntimeException {

    public OrderFailureException(String message) {
        super(message);
    }
}