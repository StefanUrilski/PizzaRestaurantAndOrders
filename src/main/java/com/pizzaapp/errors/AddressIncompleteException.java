package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error occurred during address operation.")
public class AddressIncompleteException extends RuntimeException {

    public AddressIncompleteException(String message) {
        super(message);
    }
}
