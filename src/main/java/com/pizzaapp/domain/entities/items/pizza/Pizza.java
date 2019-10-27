package com.pizzaapp.domain.entities.items.pizza;

import com.pizzaapp.domain.entities.items.MenuItem;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza extends MenuItem {

    private String description;
    private Size size;
    private Dough dough;
    private Set<Ingredient> ingredients;

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = Size.class)
    @JoinTable(name = "pizzas_size",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Enumerated(EnumType.STRING)
    public Dough getDough() {
        return dough;
    }

    public void setDough(Dough dough) {
        this.dough = dough;
    }

    @ManyToMany(targetEntity = Ingredient.class, fetch = FetchType.EAGER)
    @JoinTable(name = "pizzas_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    public Set<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
