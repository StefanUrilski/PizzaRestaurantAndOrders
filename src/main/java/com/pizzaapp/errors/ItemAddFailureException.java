package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error occurred during item add.")
public class ItemAddFailureException extends RuntimeException {

    public ItemAddFailureException(String message) {
        super(message);
    }
}
