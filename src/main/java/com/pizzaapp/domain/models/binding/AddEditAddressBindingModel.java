package com.pizzaapp.domain.models.binding;

import com.pizzaapp.domain.entities.Town;

public class AddEditAddressBindingModel {

    private String id;
    private Town town;
    private String street;
    private Integer number;
    private String phoneNumber;
    private Integer floor;
    private Integer block;
    private Integer apartment;
    private Character entrance;

    public String getId() {
        return this.id;
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
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getFloor() {
        return this.floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getBlock() {
        return this.block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public Integer getApartment() {
        return this.apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public Character getEntrance() {
        return this.entrance;
    }

    public void setEntrance(Character entrance) {
        this.entrance = entrance;
    }
}
