package com.pizzaapp.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "couriers")
public class Courier extends BaseEntity {

    private LocalDateTime taken;
    private LocalDateTime finished;
    private List<Order> orders;

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

    @OneToMany(targetEntity = User.class)
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
