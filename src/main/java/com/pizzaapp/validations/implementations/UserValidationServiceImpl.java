package com.pizzaapp.validations.implementations;

import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.validations.UserValidationService;
import com.pizzaapp.validations.ValidationConstants;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidationServiceImpl implements UserValidationService {

    private Pattern pattern = Pattern.compile(ValidationConstants.EMAIL_PATTERN);

    @Override
    public boolean isValid(UserServiceModel userServiceModel) {
        Matcher matcher = pattern.matcher(userServiceModel.getEmail());

        if (!matcher.matches()) {
            return false;
        }

        if (userServiceModel.getFullName().length() < 3 || userServiceModel.getFullName().length() > 10) {
            return false;
        }

        return userServiceModel.getPassword().length() >= 2 && userServiceModel.getPassword().length() <= 20;
    }
}
