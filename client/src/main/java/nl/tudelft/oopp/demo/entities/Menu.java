package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Creates and maneges an object for the current menus of the different restaurants in the database when in client side
 */
public class Menu {

    private int id;
    private String name;

    private Dish dish;

    private Restaurant restaurant;

    Set<Dish> dishes = new HashSet<>();

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
}
