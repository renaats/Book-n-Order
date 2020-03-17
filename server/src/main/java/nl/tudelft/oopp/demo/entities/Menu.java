package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a menu. Holds all necessary information about the menu that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Restaurant as a foreign key.
 */
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Restaurant restaurant;

    /**
     * Creates a new instance of Menu.
     * @param name = name of the menu.
     * @param restaurant = restaurant to which menu is associated.
     */
    public Menu(String name, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
    }

    public Menu() {

    }

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    Set<Dish> dishes = new HashSet<>();

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name)
                && Objects.equals(restaurant, menu.restaurant)
                && Objects.equals(dishes, menu.dishes);
    }
}
