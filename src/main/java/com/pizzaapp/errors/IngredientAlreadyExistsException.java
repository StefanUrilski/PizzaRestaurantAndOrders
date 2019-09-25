package com.pizzaapp.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Ingredient with the given name already exists.")
public class IngredientAlreadyExistsException extends RuntimeException {

    public IngredientAlreadyExistsException(String message) {
        super(message);
    }
}
