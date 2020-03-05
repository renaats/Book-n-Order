package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.RoomReservation;

public interface RoomReservationService {
    String add(int roomId, String email, long fromTimeMs, long toTimeMs);

    String update(int id, String attribute, String value);

    String delete(int id);

    Iterable<RoomReservation> all();
}
