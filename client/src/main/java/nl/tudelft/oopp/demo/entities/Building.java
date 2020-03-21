package nl.tudelft.oopp.demo.entities;

import javafx.beans.property.StringProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Building {

    private int id;
    private String name;
    private String street;
    private int houseNumber;

    Set<Room> rooms = new HashSet<>();

    Set<Bike> bikes = new HashSet<>();

    Set<Restaurant> restaurants = new HashSet<>();

    /** Creates a new instance of Building.
     * @param name the name of the building.
     * @param street the street name of the building's address.
     * @param houseNumber the house number of the building.
     */
    public Building(String name, String street, int houseNumber) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Building() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public boolean hasRooms() {
        return rooms.size() > 0;
    }

    /**
     * Checks if this building has a room with the specified name.
     * @param name = the name of the room.
     * @return true if this building contains the specified room, false otherwise.
     */
    public boolean hasRoomWithName(String name) {
        for (Room room: rooms) {
            if (room.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        return houseNumber == building.houseNumber
                && Objects.equals(name, building.name)
                && Objects.equals(street, building.street)
                && Objects.equals(rooms, building.rooms);
    }

}
