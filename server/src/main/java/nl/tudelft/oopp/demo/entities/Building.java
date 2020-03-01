package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity // This tells Hibernate to make a table out of this class
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String street;
    private int houseNumber;

    @OneToMany(mappedBy = "building")
    Set<Room> rooms = new HashSet<>();

    public void setId(Integer id) {
        this.id = id;
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


    public void addRoom(Room room) {
        rooms.add(room);
    }

    public boolean hasRooms() {
        return rooms.size() > 0;
    }

    /**
     * Checks if this building has a room with the specified name.
     * @param name = the name of the room
     * @return a boolean whether this building has the specified room
     */
    public boolean hasRoomWithName(String name) {
        for (Room room: rooms) {
            if (room.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
