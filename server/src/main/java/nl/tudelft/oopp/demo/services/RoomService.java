package nl.tudelft.oopp.demo.services;

import java.util.Set;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public interface RoomService {
    int add(String name, String faculty, boolean facultySpecific, boolean screen, boolean projector, int buildingId, int nrPeople, int plugs);

    int update(int id, String attribute, String value);

    int delete(int id);

    Iterable<Room> all();

    Room find(int id);

    Set<RoomReservation> reservations(int id);
}
