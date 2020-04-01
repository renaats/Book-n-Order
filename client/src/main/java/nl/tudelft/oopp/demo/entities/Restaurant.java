package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

/**
 * Manages the Restaurant object that is retrieved from the server
 */
public class Restaurant {
    private int id;

    private Building building;

    private String name;
    private String email;
    private Menu menu;

    /**
     * Creates a new instance of Restaurant.
     * @param building = building in which restaurant is located.
     * @param name = name of the restaurant.
     * @param menu = menu of the restaurant.
     */
    public Restaurant(Building building, String name, String email, Menu menu) {
        this.building = building;
        this.name = name;
        this.email = email;
        this.menu = menu;
    }

    public Restaurant() {

    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant restaurant = (Restaurant) o;
        return name.equals(restaurant.name)
                && Objects.equals(email, restaurant.email)
                && Objects.equals(building, restaurant.building);
    }
}
