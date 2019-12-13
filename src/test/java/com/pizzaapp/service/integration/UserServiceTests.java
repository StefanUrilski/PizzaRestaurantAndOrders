package com.pizzaapp.service.integration;

import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.service.UserService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceTests extends TestBase {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_whenUserNotFound_shouldThrowException() {
        when(userRepository.findByUsername("someEmail"))
                .thenReturn(Optional.empty());

        userService.loadUserByUsername("email");
    }
}
