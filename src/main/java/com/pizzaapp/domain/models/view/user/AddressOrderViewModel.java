package com.pizzaapp.domain.models.view.user;

public class AddressOrderViewModel {

    private String id;
    private String location;

    public AddressOrderViewModel() {
    }

    public AddressOrderViewModel(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
