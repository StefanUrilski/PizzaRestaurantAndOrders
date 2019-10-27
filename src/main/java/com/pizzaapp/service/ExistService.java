package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.errors.ItemAlreadyExistsException;
import com.pizzaapp.errors.NameNotFoundException;

public class ExistService {

    public static void checkIfItemExistThrowException(Object entity) {
        if (entity == null) {
            throw new NameNotFoundException(Constants.WRONG_NON_EXISTENT_NAME);
        }
    }

    public static void checkIfItemNotExistThrowException(Object entity, String name) {
        if (entity != null) {
            throw new ItemAlreadyExistsException(String.format(Constants.ITEM_ALREADY_EXISTS, name));
        }
    }
}
