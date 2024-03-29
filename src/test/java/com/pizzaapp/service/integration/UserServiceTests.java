package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.UserRole;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.errors.IdNotFoundException;
import com.pizzaapp.repository.RoleRepository;
import com.pizzaapp.repository.UserRepository;
import com.pizzaapp.service.UserService;
import com.pizzaapp.testBase.TestBase;
import com.pizzaapp.validations.UserValidationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTests extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserValidationService userValidation;

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

        when(userValidation.isValid(user))
                .thenReturn(true);

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
    public void getUserByEmail_whenUserNotFound_shouldThrowException() {
        when(userRepository.findByUsername("email"))
                .thenReturn(Optional.empty());

        service.getUserByEmail("email");
    }

    @Test
    public void getUserByEmail_whenUserFound_shouldReturnSameUser() {
        User user = getUser();

        when(userRepository.findByUsername("email"))
                .thenReturn(Optional.of(user));

        UserServiceModel loadedUser = service.getUserByEmail("email");

        assertEquals(user.getId(), loadedUser.getId());
        assertEquals(user.getUsername(), loadedUser.getEmail());
        assertEquals(user.getFullName(), loadedUser.getFullName());
    }

    @Test
    public void getUserById_whenIdExist_shouldReturnSameUser(){
        User user = getUser();

        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));

        UserServiceModel userService = service.getUserById("1");

        assertEquals(user.getId(), userService.getId());
        assertEquals(user.getUsername(), userService.getEmail());
        assertEquals(user.getFullName(), userService.getFullName());
    }

    @Test(expected = IdNotFoundException.class)
    public void getUserById_whenUserNotFound_shouldThrowException() {
        when(userRepository.findById("22"))
                .thenReturn(Optional.empty());

        service.getUserById("22");
    }

    @Test
    public void getAllUsersOrderedAlphabetically_shouldReturnAllUsers() {
        User u1 = new User();
        User u2 = new User();

        u1.setUsername("userOne");
        u2.setUsername("userTwo");

        List<User> actual = new ArrayList<>(List.of(u1, u2));

        when(userRepository.findAllOrderedAlphabetically())
                .thenReturn(actual);

        List<UserServiceModel> expected = service.getAllUsersOrderedAlphabetically();

        assertEquals(actual.size(), expected.size());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void editUserRole_whenNotRoleFound_shouldThrowException() {
        when(userRepository.findByUsername("some"))
                .thenReturn(Optional.empty());

        service.editUserRole("some", "user");
    }

    @Test
    public void editUserRole_whenChangeRole_shouldRoleBeChanged() {
        User user = new User();
        user.setUsername("email");

        when(userRepository.findByUsername("some"))
                .thenReturn(Optional.of(user));

        when(roleRepository.findByAuthority("user"))
                .thenReturn(Optional.of(new UserRole("User")));

        boolean changedRole = service.editUserRole("some", "user");

        assertTrue(changedRole);
        assertEquals(1, user.getAuthorities().size());
    }
}
