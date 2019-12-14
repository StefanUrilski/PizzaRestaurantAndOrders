package com.pizzaapp.validation.unit;

import com.pizzaapp.domain.entities.Town;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.validations.AddressValidationService;
import com.pizzaapp.validations.implementations.AddressValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressValidationServiceTests {

    private AddressServiceModel address;

    private AddressValidationService validation;

    @Before
    public void initCorrectUser() {
        validation = new AddressValidationServiceImpl();

        address = new AddressServiceModel();

        address.setTown(Town.Sofia);
        address.setStreet("someStreet");
        address.setNumber(22);
        address.setPhoneNumber("+359884551045");
        address.setEntrance('A');
        address.setOwner(new UserServiceModel());
    }

    @Test
    public void isValid_whenDataCorrect_shouldReturnTrue() {
        assertTrue(validation.isValid(address));
    }

    @Test
    public void isValid_whenTownIsNull_shouldReturnFalse() {
        address.setTown(null);
        assertFalse(validation.isValid(address));
    }

    @Test
    public void isValid_whenStreetIsNull_shouldReturnFalse() {
        address.setStreet(null);
        assertFalse(validation.isValid(address));
    }

    @Test
    public void isValid_whenStreetIsEmpty_shouldReturnFalse() {
        address.setStreet(" ");
        assertFalse(validation.isValid(address));
    }

    @Test
    public void isValid_whenNumberIsNull_shouldReturnFalse() {
        address.setNumber(null);
        assertFalse(validation.isValid(address));
    }

    @Test
    public void isValid_whenPhoneNumberDontCorrect_shouldReturnFalse() {
        address.setPhoneNumber("531");
        assertFalse(validation.isValid(address));
    }

    @Test
    public void isValid_whenEntranceIsNull_shouldReturnFalse() {
        address.setEntrance(null);
        assertFalse(validation.isValid(address));
    }

    @Test
    public void isValid_whenOwnerIsNull_shouldReturnFalse() {
        address.setOwner(null);
        assertFalse(validation.isValid(address));
    }
}
