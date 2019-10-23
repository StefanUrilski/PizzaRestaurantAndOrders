package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.UserRegisterBindingModel;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.service.UserService;
import com.pizzaapp.web.validations.user.UserRegisterValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRegisterValidator userRegisterValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, UserRegisterValidator userRegisterValidator, ModelMapper modelMapper) {
        this.userService = userService;
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
        userService.registerUser(userServiceModel);

        return redirect("/");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return view("users/login-user");
    }


}
