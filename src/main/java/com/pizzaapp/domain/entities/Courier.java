package com.pizzaapp.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "couriers")
public class Courier extends BaseEntity {

    private String email;
    private LocalDateTime taken;
    private LocalDateTime finished;
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

    @Column(name = "taken_on")
    public LocalDateTime getTaken() {
        return taken;
    }

    public void setTaken(LocalDateTime taken) {
        this.taken = taken;
    }

    @Column(name = "finished_on")
    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
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
