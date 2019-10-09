package com.pizzaapp.domain.models.service;

import com.pizzaapp.domain.entities.Town;

public class AddressServiceModel {

    private String id;
    private Town town;
    private String street;
    private Integer number;
    private String phoneNumber;
    private Integer floor;
    private Integer block;
    private Integer apartment;
    private Character entrance;
    private UserServiceModel owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public Character getEntrance() {
        return entrance;
    }

    public void setEntrance(Character entrance) {
        this.entrance = entrance;
    }

    public UserServiceModel getOwner() {
        return owner;
    }

    public void setOwner(UserServiceModel owner) {
        this.owner = owner;
    }
}
