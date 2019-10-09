package com.pizzaapp.domain.entities.items;

import com.pizzaapp.domain.entities.ingredients.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza extends MenuItem {

    private String description;
    private Sauce sauce;
    private Set<Spice> spices;
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

    @ManyToOne(targetEntity = Sauce.class)
    @JoinTable(name = "pizzas_sauces"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "sauce_id")
    )
    public Sauce getSauce() {
        return this.sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    @ManyToMany(targetEntity = Spice.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_spices"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "spice_id")
    )
    public Set<Spice> getSpices() {
        return this.spices;
    }

    public void setSpices(Set<Spice> spices) {
        this.spices = spices;
    }

    @ManyToMany(targetEntity = Cheese.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_cheeses"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "cheese_id")
    )
    public Set<Cheese> getCheeses() {
        return this.cheeses;
    }

    public void setCheeses(Set<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    @ManyToMany(targetEntity = Meat.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_meats"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "meat_id")
    )
    public Set<Meat> getMeats() {
        return this.meats;
    }

    public void setMeats(Set<Meat> meats) {
        this.meats = meats;
    }

    @ManyToOne(targetEntity = Dough.class)
    @JoinTable(name = "pizzas_doughs"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "dough_id")
    )
    public Dough getDough() {
        return dough;
    }

    public void setDough(Dough dough) {
        this.dough = dough;
    }

    @ManyToOne(targetEntity = Size.class)
    @JoinTable(name = "pizzas_sizes"
            , joinColumns = @JoinColumn(name = "pizza_id")
            , inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
