package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the Menu object that is retrieved from the server
 */
public class Menu {

    private int id;
    private String name;

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
