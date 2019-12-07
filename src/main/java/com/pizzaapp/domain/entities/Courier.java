package com.pizzaapp.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "couriers")
public class Courier extends BaseEntity {

    private String email;
    private List<Order> orders;

    public Courier() {
        this.orders = new ArrayList<>();
    }

    public Courier(String email) {
        this();
        this.email = email;
    }

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(targetEntity = Order.class, fetch = FetchType.EAGER)
    @JoinTable(name = "couriers_orders",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
