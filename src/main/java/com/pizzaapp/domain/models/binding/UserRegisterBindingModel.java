package com.pizzaapp.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterBindingModel {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterBindingModel() {
    }

    @NotNull(message = "Email cannot be null.")
    @NotEmpty(message = "Email cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email.")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Password cannot be null.")
    @NotEmpty(message = "Password cannot be empty.")
    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 symbols long.")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Password cannot be null.")
    @NotEmpty(message = "Password cannot be empty.")
    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 symbols long.")
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = "Full name cannot be null.")
    @NotEmpty(message = "Full name cannot be empty.")
    @Length(min = 2, message = "Full name must be at least 2 symbols long.")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = "Full name must start with capital letter.")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
