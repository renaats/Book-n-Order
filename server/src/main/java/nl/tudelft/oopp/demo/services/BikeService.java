package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Bike;

public interface BikeService {
    String add(int buildingId, boolean available);

    String update(int id, String attribute, String value);

    String delete(int id);

    Iterable<Bike> all();

    Bike find(int id);
}
