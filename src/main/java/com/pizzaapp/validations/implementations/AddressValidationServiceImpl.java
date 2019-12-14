package com.pizzaapp.validations.implementations;

import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.validations.AddressValidationService;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddressValidationServiceImpl implements AddressValidationService {

    @Override
    public boolean isValid(AddressServiceModel address) {
        if (address.getTown() == null) {
            return false;
        }

        if (address.getStreet().isEmpty()) {
            return false;
        }

        if (address.getNumber() == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[+]*[0-9]{10,12}$");
        Matcher matcher = pattern.matcher(address.getPhoneNumber());

        if (!matcher.matches()) {
            return false;
        }

        if (address.getEntrance() == null) {
            return false;
        }

        return address.getOwner() != null;
    }
}
