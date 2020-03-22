package nl.tudelft.oopp.demo.entities;

import java.util.Set;

public class Dish {

    private int id;
    private String name;
    private Menu menu;
    private Set<FoodOrder> foodOrders;

    /**
     * Creates a new instance of Dish.
     * @param name = name of the dish.
     * @param menu = menu to which dish is associated.
     */
    public Dish(String name, Menu menu) {
        this.name = name;
        this.menu = menu;
    }

    public Dish() {

    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodOrders(Set<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

}
