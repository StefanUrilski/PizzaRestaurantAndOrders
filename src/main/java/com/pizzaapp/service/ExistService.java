package com.pizzaapp.service;

import com.pizzaapp.common.Constants;
import com.pizzaapp.errors.ItemAlreadyExistsException;
import com.pizzaapp.errors.PropertyNotFoundException;

public class ExistService {

    public static void checkIfItemNotExistThrowException(Object entity) {
        if (entity == null) {
            throw new PropertyNotFoundException(Constants.WRONG_NON_EXISTENT_PROPERTY);
        }
    }

    public static void checkIfItemExistThrowException(Object entity, String name) {
        if (entity != null) {
            throw new ItemAlreadyExistsException(String.format(Constants.ITEM_ALREADY_EXISTS, name));
        }
    }
}
