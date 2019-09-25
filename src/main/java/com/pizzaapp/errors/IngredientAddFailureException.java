package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error occurred during ingredient add.")
public class IngredientAddFailureException extends RuntimeException {

    public IngredientAddFailureException(String message) {
        super(message);
    }
}
