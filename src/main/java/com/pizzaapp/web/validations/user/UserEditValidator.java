package com.pizzaapp.web.validations.user;

import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.models.binding.UserEditBindingModel;
import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.web.validations.ValidationConstants;
import com.pizzaapp.web.validations.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Validator
public class UserEditValidator implements org.springframework.validation.Validator {

    private Pattern pattern = Pattern.compile(ValidationConstants.EMAIL_PATTERN);
    private Matcher matcher;


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserEditValidator(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEditBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserEditBindingModel userEditBindingModel = (UserEditBindingModel) o;

        User user = userRepository.findByUsername(userEditBindingModel.getEmail()).orElse(null);

        if (!bCryptPasswordEncoder.matches(userEditBindingModel.getPassword(), user.getPassword())) {
            errors.rejectValue(
                    "password",
                    ValidationConstants.WRONG_PASSWORD,
                    ValidationConstants.WRONG_PASSWORD
            );
        }

        if (userEditBindingModel.getPassword() != null && !userEditBindingModel.getNewPassword().equals(userEditBindingModel.getConfirmPassword())) {
            errors.rejectValue(
                    "newPassword",
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH,
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if (!user.getUsername().equals(userEditBindingModel.getEmail()) && userRepository.findByUsername(userEditBindingModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userEditBindingModel.getEmail()),
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userEditBindingModel.getEmail())
            );
        }

        if (userRepository.findByUsername(userEditBindingModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userEditBindingModel.getEmail()),
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, userEditBindingModel.getEmail())
            );
        }

        matcher = pattern.matcher(userEditBindingModel.getEmail());

        if (!matcher.matches()) {
            errors.rejectValue(
                    "email",
                    ValidationConstants.INVALID_EMAIL,
                    ValidationConstants.INVALID_EMAIL
            );
        }

    }
}
