package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Represents a building. Holds all necessary information about the building that is then stored in the database.
 * Is uniquely identified by its id.
 */
@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String street;
    private int houseNumber;

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

    @JsonIgnore
    @OneToMany(mappedBy = "building")
    Set<Room> rooms = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    Set<Bike> bikes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "building")
    Set<Restaurant> restaurants = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "building")
    Set<BuildingHours> buildingHours = new HashSet<>();

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
