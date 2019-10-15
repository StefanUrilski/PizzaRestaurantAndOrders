package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.UserEditBindingModel;
import com.pizzaapp.domain.models.view.user.AddressViewModel;
import com.pizzaapp.web.validations.user.UserEditValidator;
import com.pizzaapp.web.validations.user.UserRegisterValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.pizzaapp.domain.models.binding.UserRegisterBindingModel;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.errors.UserEditFailureException;
import com.pizzaapp.errors.UserRegisterFailureException;
import com.pizzaapp.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final UserEditValidator userEditValidator;
    private final UserRegisterValidator userRegisterValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, UserEditValidator userEditValidator, UserRegisterValidator userRegisterValidator, ModelMapper modelMapper) {
        this.userService = userService;
        this.userEditValidator = userEditValidator;
        this.userRegisterValidator = userRegisterValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute("model") UserRegisterBindingModel userRegisterBindingModel) {
        return view("users/register-user", "model", userRegisterBindingModel);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("model") UserRegisterBindingModel model,
                                        BindingResult bindingResult) {

        userRegisterValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            model.setPassword(null);
            model.setConfirmPassword(null);
            return view("users/register-user", "model", model);
        }

        UserServiceModel userServiceModel = modelMapper.map(model, UserServiceModel.class);
        this.userService.registerUser(userServiceModel);

        return redirect("/");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return view("users/login-user");
    }


    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@ModelAttribute("userRegisterBindingModel") UserEditBindingModel userEditBindingModel, Principal principal) {
        userEditBindingModel = modelMapper.map(userService.extractUserByEmail(principal.getName()), UserEditBindingModel.class);

        return view("users/profile-user", "userRegisterBindingModel", userEditBindingModel);
    }

}
