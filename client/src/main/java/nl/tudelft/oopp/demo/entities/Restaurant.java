package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Manages the Restaurant object that is retrieved from the server
 */
public class Restaurant {
    private int id;
    private Building building;
    private String name;
    private Menu menu;

    /**
     * Creates a new instance of Restaurant.
     * @param building = building in which restaurant is located.
     * @param name = name of the restaurant.
     * @param menu = menu of the restaurant.
     */
    public Restaurant(Building building, String name, Menu menu) {
        this.building = building;
        this.name = name;
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

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getBuildingNameProperty() {
        String name = getBuilding().getName();
        return new SimpleStringProperty(name);
    }

    /**
     * Makes the table list the menu name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getMenuNameProperty() {
        String name = getMenu().getName();
        return new SimpleStringProperty(name);
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
                && Objects.equals(building, restaurant.building);
    }
}
