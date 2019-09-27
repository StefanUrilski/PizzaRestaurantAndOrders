package com.pizzaapp.domain.models.view.user;

public class AddressViewModel {

    private String id;
    private String name;
    private String street;
    private Integer number;
    private Integer postCode;
    private String municipality;
    private String phoneNumber;
    private Integer floor;
    private Integer block;
    private Integer apartment;
    private Character entrance;

    public AddressViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getPostCode() {
        return this.postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
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
