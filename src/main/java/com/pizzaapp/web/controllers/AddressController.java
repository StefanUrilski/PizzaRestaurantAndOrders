package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.binding.AddEditAddressBindingModel;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.domain.models.view.user.AddressOrderViewModel;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("users/addresses")
@PreAuthorize("hasRole('ROLE_USER') && !hasRole('ROLE_COURIER')")
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
    public ModelAndView allAddresses(Principal principal) {
        AddressViewModel[] addresses = modelMapper.map(
                addressService.getUserAddressesOrderedByTown(principal.getName()),
                AddressViewModel[].class
        );

        return view("addresses/all-addresses", "addresses", addresses);
    }

    @GetMapping("/add")
    public ModelAndView addAddress(){
        return view("addresses/add-address");
    }

    @PostMapping("/add")
    public ModelAndView addAddressConfirm(@ModelAttribute AddEditAddressBindingModel addressBindingModel, Principal principal){
        AddressServiceModel addressServiceModel = modelMapper.map(addressBindingModel, AddressServiceModel.class);

        addressServiceModel.setOwner(userService.getUserByEmail(principal.getName()));
        addressService.addAddress(addressServiceModel);

        return redirect("/users/addresses/all");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editAddress(@PathVariable String id){
        AddEditAddressBindingModel address = modelMapper.map(
                addressService.getAddressById(id),
                AddEditAddressBindingModel.class
        );
        return view("addresses/edit-address", "address", address);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editAddressConfirm(@ModelAttribute AddEditAddressBindingModel addressBindingModel, Principal principal){
        AddressServiceModel addressServiceModel = modelMapper.map(addressBindingModel, AddressServiceModel.class);

        addressServiceModel.setOwner(userService.getUserByEmail(principal.getName()));
        addressService.editAddress(addressServiceModel);

        return redirect("/users/addresses/all");
    }

    @GetMapping("/fetchAll")
    @ResponseBody
    public List<AddressOrderViewModel> fetchAllAddresses(Principal principal) {
        List<AddressOrderViewModel> allAddresses = new ArrayList<>();

        addressService.getUserAddressesOrderedByTown(principal.getName())
                .forEach(address -> {
                    String location = String.format("%s, street %s, № %s",
                            address.getTown(),
                            address.getStreet(),
                            address.getNumber()
                    );

                    allAddresses.add(new AddressOrderViewModel(address.getId(), location));
                });


        return allAddresses;
    }
}
