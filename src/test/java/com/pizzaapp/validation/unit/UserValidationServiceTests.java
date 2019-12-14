package com.pizzaapp.validation.unit;

import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.validations.UserValidationService;
import com.pizzaapp.validations.implementations.UserValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidationServiceTests {

    private UserServiceModel user;

    private UserValidationService validation;

    @Before
    public void initCorrectUser() {
        validation = new UserValidationServiceImpl();

        user = new UserServiceModel();

        user.setEmail("correct@em.il");
        user.setFullName("someName");
        user.setPassword("somePassword");
    }

    @Test
    public void isValid_whenDataCorrect_shouldReturnTrue() {
        assertTrue(validation.isValid(user));
    }

    @Test
    public void isValid_whenEmailDontCorrect_shouldReturnFalse() {
        user.setEmail("wrongEmail");
        assertFalse(validation.isValid(user));
    }

    @Test
    public void isValid_whenFullNameIsVeryLong_shouldReturnFalse() {
        user.setFullName("someVeryLongName");
        assertFalse(validation.isValid(user));
    }

    @Test
    public void isValid_whenFullNameIsToShort_shouldReturnFalse() {
        user.setFullName("No");
        assertFalse(validation.isValid(user));
    }

    @Test
    public void isValid_whenPasswordIsVeryLong_shouldReturnFalse() {
        user.setPassword("someVeryLongWrongPassword");
        assertFalse(validation.isValid(user));
    }

    @Test
    public void isValid_whenPasswordIsToShort_shouldReturnFalse() {
        user.setPassword("E");
        assertFalse(validation.isValid(user));
    }
}
