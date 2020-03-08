package nl.tudelft.oopp.demo.services;

import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

public interface BuildingService {
    int add(String name, String street, int houseNumber);

    int update(int id, String attribute, String value);

    int delete(int id);

    Iterable<Building> all();

    Building find(int id);

    Set<Room> rooms(int id);
}
