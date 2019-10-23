package com.pizzaapp.web.validations.user;

import com.pizzaapp.domain.models.binding.UserRegisterBindingModel;
import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.web.validations.ValidationConstants;
import com.pizzaapp.web.validations.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

    private Pattern pattern = Pattern.compile(ValidationConstants.EMAIL_PATTERN);
    private Matcher matcher;

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) object;

        if (userRepository.findByUsername(userRegisterBindingModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userRegisterBindingModel.getEmail()),
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userRegisterBindingModel.getEmail())
            );
        }

        matcher = pattern.matcher(userRegisterBindingModel.getEmail());

        if (!matcher.matches()) {
            errors.rejectValue(
                    "email",
                    ValidationConstants.INVALID_EMAIL,
                    ValidationConstants.INVALID_EMAIL
            );
        }

        if (userRegisterBindingModel.getFullName().length() < 3 || userRegisterBindingModel.getFullName().length() > 10) {
            errors.rejectValue(
                    "fullName",
                    ValidationConstants.USERNAME_LENGTH,
                    ValidationConstants.USERNAME_LENGTH
            );
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH,
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if (userRegisterBindingModel.getPassword().length() < 2 || userRegisterBindingModel.getPassword().length() > 20) {
            errors.rejectValue(
                    "password",
                    ValidationConstants.PASSWORD_LENGTH,
                    ValidationConstants.PASSWORD_LENGTH
            );
        }

    }
}
