package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a restaurant. Holds all necessary information about the restaurant that is then stored in the database.
 * Is uniquely identified by its id.
 * Contains Building as a foreign key.
 * Contains Menu as a foreign key.
 */
public class Restaurant {
    private int id;
    private Building building;
    private String name;
    private String email;
    private Menu menu;
    private int feedbackCounter;

    /**
     * Creates a new instance of Restaurant.
     * @param building = building in which restaurant is located.
     * @param name = name of the restaurant.
     */
    public Restaurant(Building building, String name, String email) {
        this.building = building;
        this.name = name;
        this.email = email;
    }

    public Restaurant() {

    }

    Set<RestaurantHours> restaurantHours = new HashSet<>();

    public void setId(int id) {
        this.id = id;
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

    public int getFeedbackCounter() {
        return feedbackCounter;
    }

    public void setFeedbackCounter(int feedbackCounter) {
        this.feedbackCounter = feedbackCounter;
    }

    public Menu getMenu() {
        return menu;
    }

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getBuildingNameProperty() {
        String name = getBuilding().getName();
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
                && Objects.equals(email, restaurant.email)
                && Objects.equals(building, restaurant.building);
    }
}
