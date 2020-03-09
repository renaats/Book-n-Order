package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Bike;

public interface BikeService {
    int add(int buildingId, boolean available);

    int update(int id, String attribute, String value);

    int delete(int id);

    Iterable<Bike> all();

    Bike find(int id);
}
