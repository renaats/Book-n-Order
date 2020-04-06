package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnBodyOfResponse;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnErrorMessage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;

/**
 * Controls all client to server communication related to the room entity
 * Sends the appropriate HTTP request depending on the method
 */
public class RoomServerCommunication {

    /**
     * Retrieves a JSON string representation of all rooms from the server.
     * @return the body of the response from the server.
     */
    public static String getRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Updates a given attribute of a room.
     * @param id = the id of the room.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the error message corresponding to the server's response.
     */
    public static String updateRoom(int id, String attribute, String changeValue) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates addRoom to the database.
     * @param name room name.
     * @param buildingId building ID.
     * @param studySpecific is it specific for a faculty.
     * @param screen does the room have a screen.
     * @param projector does the room have a projector.
     * @param capacity capacity of the room in people.
     * @param plugs amount of available plugs.
     * @param status status of the room.
     * @return the error message corresponding to the server's response.
     */
    public static String addRoom(String name,
                                 int buildingId, String studySpecific,
                                 boolean screen, boolean projector,
                                 int capacity, int plugs, String status) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/add?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&studySpecific=" + URLEncoder.encode(studySpecific, StandardCharsets.UTF_8) + "&screen=" + screen + "&projector=" + projector + "&buildingId=" + buildingId + "&capacity=" + capacity + "&plugs=" + plugs + "&status=" + status)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Queries the rooms on specific attributes.
     * @param query the query parameters.
     * @return A JSON list of rooms matching the query.
     */
    public static String filterRooms(String query) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room/filter?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8))).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a room by given id.
     * @param roomId = the id of the room.
     * @return The body of the response from the server.
     */
    public static String findRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/find/" + roomId)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a room by given name.
     * @param name = the name of the room.
     * @return The body of the response from the server.
     */
    public static String findRoomByName(String name) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/findName/" + URLEncoder.encode(name, StandardCharsets.UTF_8))).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a room reservation by given id.
     * @param roomReservationId = the id of the room.
     * @return The body of the response from the server.
     */
    public static String findRoomReservation(int roomReservationId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/find/" + roomReservationId)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all room reservations from the server.
     * @return the body of a get request to the server.
     */
    public static String getRoomReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all future and current room reservations from the server.
     * @return the body of the response from the server.
     */
    public static String getAllFutureRoomReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/future")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all previous room reservations from the server.
     * @return the body of the response from the server.
     */
    public static String getAllPreviousRoomReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/past")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all future and current room reservations for some room.
     * @param roomId the id of the room.
     * @return the body of the response from the server.
     */
    public static String getAllFutureRoomReservationsForRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/futureAdmin?roomId=" + roomId)).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all previous room reservations for some room.
     * @param roomId the id of the room.
     * @return the body of the response from the server.
     */
    public static String getAllPreviousRoomReservationsForRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/pastAdmin?roomId=" + roomId)).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all active room reservations from the server.
     * @return the body of the response from the server.
     */
    public static String getAllActiveRoomReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/active")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Communicates addRoomReservation to the database.
     * @param roomId id of the room.
     * @param fromTimeMs start time of the reservation in milliseconds.
     * @param toTimeMs end time of the reservation in milliseconds.
     * @return the error message corresponding to the server's response.
     */
    public static String addRoomReservation(int roomId, long fromTimeMs, long toTimeMs) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/add?roomId=" + roomId + "&fromTimeMs=" + fromTimeMs + "&toTimeMs=" + toTimeMs)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of a room reservation.
     * @param id = the id of the room reservation.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the error message corresponding to the server's response.
     */
    public static String updateRoomReservation(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes a room reservation from the database.
     * @param id = the id of the room reservation.
     * @return the error message corresponding to the server's response.
     */
    public static String deleteRoomReservation(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/delete?id=" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Cancels a room reservation for the user who sends the request.
     * @param id = the id of the room reservation.
     * @return the error message corresponding to the server's response.
     */
    public static String cancelRoomReservation(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/cancel/" + id)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes a room from the database.
     * @param id = the id of the room.
     * @return the error message corresponding to the server's response.
     */
    public static String deleteRoom(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Finds reservations for a specific room.
     * @param id = id of the room.
     * @return the body of the response from the server.
     */
    public static String findReservationForRoom(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/room/" + id)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }
}
