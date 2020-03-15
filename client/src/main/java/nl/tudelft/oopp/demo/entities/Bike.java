package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Bike {

    private int id;
    private Building location;
    private boolean available;

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
