package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.UserEditBindingModel;
import com.pizzaapp.domain.models.view.user.AddressViewModel;
import com.pizzaapp.web.annotations.PageTitle;
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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        return view("users/register-user", "userRegisterBindingModel", userRegisterBindingModel);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userRegisterBindingModel", "password", "Passwords don't match."));
        }

        if (bindingResult.hasErrors()) {
            return view("index", "userRegisterBindingModel", userRegisterBindingModel);
        }

        UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        if (!userService.registerUser(userServiceModel)) {
            throw new UserRegisterFailureException("Registering user " + userServiceModel.getEmail() + " failed.");
        }

        return redirect("/");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return view("users/login");
    }

    @GetMapping("/login/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView firstLogin(@ModelAttribute(name = "model") UserEditBindingModel model) {
        UserServiceModel loggedUser = userService.extractUserByEmail(model.getEmail());

        if (loggedUser.isFirstTimeLogin()) {
            return redirect("/");
        }

        userService.firstLogin(loggedUser.getId());

        return view("/users/profile/addresses", "user",  modelMapper.map(loggedUser, AddressViewModel.class));
    }

//
//    @GetMapping("/profile")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
//        UserServiceModel loggedUser = userService.findByUsername(principal.getName());
//
//        modelAndView.addObject("user", modelMapper.map(loggedUser, UserProfileViewModel.class));
//
//        return view("/user/profile", modelAndView);
//    }
//
//    @GetMapping("/edit")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView editProfile(Principal principal,
//                                    ModelAndView modelAndView,
//                                    @ModelAttribute(name = "model") UserEditBindingModel model) {
//
//        UserServiceModel userServiceModel = userService.findByUsername(principal.getName());
//        model = modelMapper.map(userServiceModel, UserEditBindingModel.class);
//        model.setPassword(null);
//        modelAndView.addObject("model", model);
//
//        return view("user/edit-profile", modelAndView);
//    }
//
//    @PatchMapping("/edit")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView editProfileConfirm(ModelAndView modelAndView,
//                                           @ModelAttribute(name = "model") UserEditBindingModel model,
//                                           BindingResult bindingResult) {
//
//        userEditValidator.validate(model, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.setOldPassword(null);
//            model.setPassword(null);
//            model.setConfirmPassword(null);
//            modelAndView.addObject("model", model);
//
//            return view("user/edit-profile", modelAndView);
//        }
//
//        UserServiceModel userServiceModel = modelMapper.map(model, UserServiceModel.class);
//        userService.editUser(userServiceModel, model.getOldPassword());
//
//        return redirect("/user/profile");
//    }
//
//    @GetMapping("/all")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PageTitle("All Users")
//    public ModelAndView allUsers(ModelAndView modelAndView) {
//        List<UserAllViewModel> users = userService.findAllUsers()
//                .stream()
//                .map(user -> {
//                    UserAllViewModel userModel = modelMapper.map(user, UserAllViewModel.class);
//                    Set<String> authorities = user.getAuthorities().stream().map(RoleServiceModel::getAuthority).collect(Collectors.toSet());
//                    userModel.setAuthorities(authorities);
//
//                    return userModel;
//                })
//                .collect(Collectors.toList());
//
//        modelAndView.addObject("users", users);
//
//        return view("user/all-users", modelAndView);
//    }
//
//    @PostMapping("/set-user/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ModelAndView setUser(@PathVariable String id) {
//        userService.setUserRole(id, "user");
//
//        return redirect("/users/all");
//    }
//
//    @PostMapping("/set-moderator/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ModelAndView setModerator(@PathVariable String id) {
//        userService.setUserRole(id, "moderator");
//
//        return redirect("/users/all");
//    }
//
//    @PostMapping("/set-admin/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ModelAndView setAdmin(@PathVariable String id) {
//        userService.setUserRole(id, "admin");
//
//        return redirect("/users/all");
//    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@ModelAttribute("userRegisterBindingModel") UserEditBindingModel userEditBindingModel, Principal principal) {
        userEditBindingModel = modelMapper.map(userService.extractUserByEmail(principal.getName()), UserEditBindingModel.class);

        return view("users/profile-user", "userRegisterBindingModel", userEditBindingModel);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@Valid @ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel, BindingResult bindingResult) {
        UserServiceModel userServiceModel = userService.extractUserByEmail(userEditBindingModel.getEmail());

        if (!bCryptPasswordEncoder.matches(userEditBindingModel.getPassword(), userServiceModel.getPassword())) {
            bindingResult.addError(new FieldError("userEditBindingModel", "password", "Incorrect password."));
        } else if (!userEditBindingModel.getNewPassword().equals(userEditBindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userEditBindingModel", "newPassword", "Passwords don't match."));
        }

        if (bindingResult.hasErrors()) {
            return view("users/profile-user", "userEditBindingModel", userEditBindingModel);
        }

        if (!userEditBindingModel.getNewPassword().equals("")) {
            userEditBindingModel.setPassword(userEditBindingModel.getNewPassword());
        }

        if (!userService.editUser(modelMapper.map(userEditBindingModel, UserServiceModel.class))) {
            throw new UserEditFailureException("Editing user " + userServiceModel.getEmail() + " failed.");
        }

        return redirect("/profiles/my");
    }

}
