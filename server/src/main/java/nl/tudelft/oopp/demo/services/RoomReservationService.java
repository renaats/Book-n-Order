package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.RoomReservation;

public interface RoomReservationService {
    int add(int roomId, String email, long fromTimeMs, long toTimeMs);

    int update(int id, String attribute, String value);

    int delete(int id);

    Iterable<RoomReservation> all();
}
