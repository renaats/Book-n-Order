package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Manages the Bike object that is retrieved from the server
 */
public class Bike {

    private int id;
    private Building location;
    private boolean available;

    /** Creates a new instance of Bike.
     * @param location the location of the building where the bike is located.
     * @param available whether the bike is available for renting.
     */
    public Bike(Building location, boolean available) {
        this.location = location;
        this.available = available;
    }

    public Bike() {

    }

    public void setLocation(Building location) {
        this.location = location;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public Building getLocation() {
        return location;
    }

    public String getLocationName() {
        return location.getName();
    }

    public String getAvailableString() {
        return ((Boolean) available).toString();
    }

    /**
     * Makes the table list the building name instead of the building object
     * @return String property, a property recognized by the tables.
     */
    public StringProperty getBuildingNameProperty() {
        String name = getLocation().getName();
        return new SimpleStringProperty(name);
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bike bike = (Bike) o;
        return available == bike.available
                && Objects.equals(location, bike.location);
    }
}
