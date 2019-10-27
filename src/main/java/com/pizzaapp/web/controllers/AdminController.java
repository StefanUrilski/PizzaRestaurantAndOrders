package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.service.UserRoleServiceModel;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.domain.models.view.user.AllUsersViewModel;
import com.pizzaapp.domain.models.view.user.UserViewModel;
import com.pizzaapp.errors.UserEditFailureException;
import com.pizzaapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private String setHighestRole(int numberOfAuthorities) {
        String role = "";

        switch (numberOfAuthorities) {
            case 1:
                role = "USER";
                break;
            case 2:
                role = "COURIER";
                break;
            case 3:
                role = "MODERATOR";
                break;
            case 4:
                role = "ADMIN";
                break;
            case 5:
                role = "ROOT";
                break;
        }

        return role;
    }

    @GetMapping("/all")
    public ModelAndView allProfiles() {
        List<AllUsersViewModel> allUsers = userService.extractAllUsersOrderedAlphabetically()
                .stream()
                .map(user -> {
                    AllUsersViewModel usersViewModel = modelMapper.map(user, AllUsersViewModel.class);
                    usersViewModel.setRole(setHighestRole(user.getAuthorities().size()));

                    return usersViewModel;
                })
                .collect(Collectors.toList());

        return view("users/all-profiles-user", "profiles", allUsers);
    }

    @GetMapping("/{id}")
    public ModelAndView profile(@PathVariable(name = "id") String id) {
        UserServiceModel userServiceModel = userService.extractUserById(id);
        UserViewModel userViewModel = modelMapper.map(userServiceModel, UserViewModel.class);
        userViewModel.setRoles(userServiceModel.getAuthorities().stream().map(UserRoleServiceModel::getAuthority).collect(Collectors.toList()));

        return view("users/details-user", "userViewModel", userViewModel);
    }

    @PostMapping("/roleEdit")
    @ResponseBody
    public void roleEditConfirm(@RequestBody String body) {
        String email = body.split("&")[0].split("=")[1].replace("%40", "@");
        String role = body.split("&")[1].split("=")[1];

        boolean result = userService.editUserRole(email, role);

        if (!result) {
            throw new UserEditFailureException("Editing user role" + email + " failed.");
        }
    }
}
