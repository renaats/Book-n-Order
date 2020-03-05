package nl.tudelft.oopp.demo.services;

import java.util.Set;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

public interface BuildingService {
    String add(String name, String street, int houseNumber);

    String update(int id, String attribute, String value);

    String delete(int id);

    Iterable<Building> all();

    Building find(int id);

    Set<Room> rooms(int id);
}
