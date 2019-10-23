package com.pizzaapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.pizzaapp.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    boolean editUser(UserServiceModel userServiceModel);

    UserServiceModel extractUserByEmail(String email);

    UserServiceModel extractUserById(String id);

    List<UserServiceModel> extractAllUsersOrderedAlphabetically();

    boolean editUserRole(String email, String role);

    boolean addAddress(UserServiceModel userServiceModel);
}
