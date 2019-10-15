package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.AddEditAddressBindingModel;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.domain.models.view.user.AddressViewModel;
import com.pizzaapp.service.AddressService;
import com.pizzaapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("users/addresses")
public class AddressController extends BaseController {

    private final UserService userService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressController(UserService userService, AddressService addressService, ModelMapper modelMapper) {
        this.userService = userService;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView AllAddresses(Principal principal) {
        AddressViewModel[] addresses = modelMapper.map(
                addressService.getUserAddressesOrderedByTown(principal.getName()),
                AddressViewModel[].class
        );

        return view("addresses/all-addresses", "addresses", addresses);
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addAddress(){
        return view("addresses/add-address");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addAddressConfirm(@ModelAttribute AddEditAddressBindingModel addressBindingModel, Principal principal){
        AddressServiceModel addressServiceModel = modelMapper.map(addressBindingModel, AddressServiceModel.class);

        addressServiceModel.setOwner(userService.extractUserByEmail(principal.getName()));
        addressService.addAddress(addressServiceModel);

        return redirect("/users/addresses/all");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editAddress(@PathVariable String id){
        AddEditAddressBindingModel address = modelMapper.map(
                addressService.getAddressById(id),
                AddEditAddressBindingModel.class
        );
        return view("addresses/edit-address", "address", address);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editAddressConfirm(@ModelAttribute AddEditAddressBindingModel addressBindingModel, Principal principal){
        AddressServiceModel addressServiceModel = modelMapper.map(addressBindingModel, AddressServiceModel.class);

        addressServiceModel.setOwner(userService.extractUserByEmail(principal.getName()));
        addressService.editAddress(addressServiceModel);

        return redirect("/users/addresses/all");
    }

}
