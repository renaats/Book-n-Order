package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Set;

public class Menu {

    private int id;
    private String name;


    private Dish dish;


    private Restaurant restaurant;

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
}
