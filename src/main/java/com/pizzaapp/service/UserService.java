package com.pizzaapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.pizzaapp.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel getUserByEmail(String email);

    UserServiceModel getUserById(String id);

    List<UserServiceModel> getAllUsersOrderedAlphabetically();

    boolean editUserRole(String email, String role);
}
