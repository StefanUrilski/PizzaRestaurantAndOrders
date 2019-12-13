package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.UserRole;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.errors.IdNotFoundException;
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
    UserService service;


    private User getUser() {
        User user = new User();
        user.setId("1");
        user.setFullName("fullName");
        user.setUsername("someEmail");
        user.setPassword("pass");

        return user;
    }


    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_whenUserNotFound_shouldThrowException() {
        when(userRepository.findByUsername("someEmail"))
                .thenReturn(Optional.empty());

        service.loadUserByUsername("email");
    }

    @Test
    public void loadUserByUsername_whenUserFound_shouldReturnSameUser() {
        User user = getUser();

        when(userRepository.findByUsername("email"))
                .thenReturn(Optional.of(user));

        UserDetails loadedUser = service.loadUserByUsername("email");

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

        service.registerUser(user);

        verify(userRepository)
                .save(any());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void extractUserByEmail_whenUserNotFound_shouldThrowException() {
        when(userRepository.findByUsername("email"))
                .thenReturn(Optional.empty());

        service.extractUserByEmail("email");
    }

    @Test
    public void extractUserByEmail_whenUserFound_shouldReturnSameUser() {
        User user = getUser();

        when(userRepository.findByUsername("email"))
                .thenReturn(Optional.of(user));

        UserServiceModel loadedUser = service.extractUserByEmail("email");

        assertEquals(user.getId(), loadedUser.getId());
        assertEquals(user.getUsername(), loadedUser.getEmail());
        assertEquals(user.getFullName(), loadedUser.getFullName());
    }

    @Test
    public void extractUserById_whenIdExist_shouldReturnSameUser(){
        User user = getUser();

        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));

        UserServiceModel userService = service.extractUserById("1");

        assertEquals(user.getId(), userService.getId());
        assertEquals(user.getUsername(), userService.getEmail());
        assertEquals(user.getFullName(), userService.getFullName());
    }

    @Test(expected = IdNotFoundException.class)
    public void extractUserById_whenUserNotFound_shouldThrowException() {
        when(userRepository.findById("22"))
                .thenReturn(Optional.empty());

        service.extractUserById("22");
    }
}
