package com.pizzaapp.web.validations;

public class ValidationConstants {

    public final static String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public final static String USERNAME_LENGTH = "FullName must be between 3 and 10 characters long!";

    public final static String PASSWORDS_DO_NOT_MATCH = "Passwords don't match!";

    public final static String EMAIL_ALREADY_EXISTS = "Email %s already exists";

    public final static String INVALID_EMAIL = "Invalid email!";

    public final static String PASSWORD_LENGTH = "Password must be between 3 and 20 characters long!";

}
