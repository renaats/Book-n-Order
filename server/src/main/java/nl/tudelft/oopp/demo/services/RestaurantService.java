package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Restaurant;

public interface RestaurantService {
    String add(int buildingId, String name);

    String update(int id, String attribute, String value);

    String delete(int id);

    Iterable<Restaurant> all();

    Restaurant find(int id);
}
