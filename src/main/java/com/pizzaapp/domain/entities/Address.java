package com.pizzaapp.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private Town town;
    private String street;
    private Integer number;
    private String phoneNumber;
    private Integer floor;
    private Integer block;
    private Integer apartment;
    private Character entrance;
    private User owner;

    @Column(name = "town")
    @Enumerated(EnumType.STRING)
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Column(name = "street_district")
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "number")
    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "floor")
    public Integer getFloor() {
        return this.floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Column(name = "block")
    public Integer getBlock() {
        return this.block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    @Column(name = "apartment")
    public Integer getApartment() {
        return this.apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    @Column(name = "entrance")
    public Character getEntrance() {
        return this.entrance;
    }

    public void setEntrance(Character entrance) {
        this.entrance = entrance;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinTable(name = "addresses_users",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
