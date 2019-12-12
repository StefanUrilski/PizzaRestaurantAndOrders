package com.pizzaapp.service.integration;

import com.pizzaapp.domain.entities.Address;
import com.pizzaapp.domain.entities.Town;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.models.service.AddressServiceModel;
import com.pizzaapp.repository.AddressRepository;
import com.pizzaapp.service.AddressService;
import com.pizzaapp.testBase.TestBase;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressServiceTests extends TestBase {

    private Address location;

    @MockBean
    AddressRepository addressRepository;

    @Autowired
    AddressService service;

    @Autowired
    ModelMapper modelMapper;

    @Override
    protected void beforeEach() {
        String street = "street";
        Integer number = 12;
        String phoneNumber = "123321";

        location = new Address();
        location.setTown(Town.Sofia);
        location.setStreet(street);
        location.setNumber(number);
        location.setPhoneNumber(phoneNumber);
        location.setOwner(new User());
    }

    @Test
    public void addAddress_shouldAddAddress() {
        AddressServiceModel address = new AddressServiceModel();

        when(addressRepository.save(any()))
                .thenReturn(new Address());

        service.addAddress(address);
        verify(addressRepository)
                .save(any());
    }

    @Test
    public void getAddressById_whenIdExist_shouldReturnSameAddress(){
        Mockito.when(addressRepository.findById("1"))
                .thenReturn(Optional.of(location));

        AddressServiceModel addressService = this.service.getAddressById("1");

        assertEquals(Town.Sofia, addressService.getTown());
        assertEquals(location.getStreet(), addressService.getStreet());
        assertEquals(location.getNumber(), addressService.getNumber());
        assertEquals(location.getPhoneNumber(), addressService.getPhoneNumber());
        assertNotNull(addressService.getOwner());
    }

    @Test
    public void getUserAddressesOrderedByTown_whenEmailExist_shouldReturnAddresses() {
        Address a1 = new Address();
        Address a2 = new Address();

        a1.setTown(Town.Plovdiv);
        a2.setTown(Town.Sofia);

        List<Address> actual = new ArrayList<>();
        actual.add(a1);
        actual.add(a2);

        Mockito.when(addressRepository.findAllUserAddressesOrderedByName("email"))
                .thenReturn(actual);

        List<AddressServiceModel> expected = service.getUserAddressesOrderedByTown("email");

        assertEquals(actual.size(), expected.size());
    }

}
