package com.pizzaapp.domain.models.service;

import java.util.List;

public class UserServiceModel {

    private String id;
    private String fullName;
    private String email;
    private String password;
    private List<AddressServiceModel> addresses;
    private List<UserRoleServiceModel> authorities;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AddressServiceModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressServiceModel> addresses) {
        this.addresses = addresses;
    }

    public List<UserRoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<UserRoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
