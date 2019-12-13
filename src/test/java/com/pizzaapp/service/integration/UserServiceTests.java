package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.UserRole;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.repository.RoleRepository;
import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.service.UserService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTests extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_whenUserNotFound_shouldThrowException() {
        when(userRepository.findByUsername("someEmail"))
                .thenReturn(Optional.empty());

        userService.loadUserByUsername("email");
    }

    @Test
    public void loadUserByUsername_whenUserFound_shouldReturnSameUser() {
        User user = new User();
        user.setUsername("someEmail");
        user.setPassword("pass");

        when(userRepository.findByUsername("email"))
                .thenReturn(Optional.of(user));

        UserDetails loadedUser = userService.loadUserByUsername("email");

        assertEquals(user.getUsername(), loadedUser.getUsername());
        assertEquals(user.getPassword(), loadedUser.getPassword());
    }

    @Test
    public void registerUser_whenUserFound_shouldReturnSameUser() {
        UserServiceModel user = new UserServiceModel();
        user.setEmail("email");
        user.setPassword("password");

        when(roleRepository.count())
                .thenReturn(1L);

        when(userRepository.count())
                .thenReturn(0L);

        when(roleRepository.findAll())
                .thenReturn(List.of(new UserRole("User")));

        userService.registerUser(user);

        verify(userRepository)
                .save(any());
    }
}
