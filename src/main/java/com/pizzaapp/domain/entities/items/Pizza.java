package com.pizzaapp.domain.entities.items;

import com.pizzaapp.domain.entities.ingredients.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza extends MenuItem {

    private String description;
    private Set<Vegetable> vegetables;
    private Set<Cheese> cheeses;
    private Set<Meat> meats;
    private Dough dough;
    private Size size;

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(targetEntity = Vegetable.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_spices",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "spice_id")
    )
    public Set<Vegetable> getVegetables() {
        return this.vegetables;
    }

    public void setVegetables(Set<Vegetable> vegetables) {
        this.vegetables = vegetables;
    }

    @ManyToMany(targetEntity = Cheese.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_cheeses",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "cheese_id")
    )
    public Set<Cheese> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(Set<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    @ManyToMany(targetEntity = Meat.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_meats",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "meat_id")
    )
    public Set<Meat> getMeats() {
        return this.meats;
    }

    public void setMeats(Set<Meat> meats) {
        this.meats = meats;
    }

    @ManyToOne(targetEntity = Dough.class)
    @JoinTable(name = "pizzas_doughs",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "dough_id")
    )
    public Dough getDough() {
        return dough;
    }

    public void setDough(Dough dough) {
        this.dough = dough;
    }

    @ManyToOne(targetEntity = Size.class)
    @JoinTable(name = "pizzas_sizes",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
