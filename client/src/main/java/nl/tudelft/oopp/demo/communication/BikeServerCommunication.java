package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnBodyOfResponse;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnErrorMessage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;

/**
 * Controls all client to server communication related to the bike entity
 * Sends the appropriate HTTP request depending on the method
 */
public class BikeServerCommunication {

    /**
     * Communicates addBike to the database
     * @param buildingId building id
     * @param available is the bike available
     * @return error message
     */
    public static String addBike(int buildingId, boolean available) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" + available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a JSON string representation of all bikes from the server.
     * @return the body of the response from the server.
     */
    public static String getBikes() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a bike by given id.
     * @param bikeId = the id of the bike.
     * @return The body of the response from the server.
     */
    public static String findBike(int bikeId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/find/" + bikeId)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Updates a given attribute of a bike.
     * @param id = the id of the bike.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the body of the response from the server.
     */
    public static String updateBike(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes a bike from the database.
     * @param id = the id of the bike.
     * @return the body of the response from the server.
     */
    public static String deleteBike(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Adds a new bike reservation.
     * @param fromBuildingId the id of the starting building.
     * @param toBuildingId the id of the ending building.
     * @param fromTimeMs the starting time in milliseconds.
     * @param toTimeMs the ending time in milliseconds.
     * @return the error message corresponding to the server's response.
     */
    public static String addBikeReservation(int fromBuildingId, int toBuildingId, long fromTimeMs, long toTimeMs) {
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/add/?fromBuilding=" + fromBuildingId + "&toBuilding=" + toBuildingId + "&fromTimeMs=" + fromTimeMs + "&toTimeMs=" + toTimeMs)).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Gets all bike reservations from the database.
     * @return the body of the response from the server.
     */
    public static String getAllBikeReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all past bike reservations for the user that sends the request.
     * @return the body of the response from the server.
     */
    public static String getAllPreviousBikeReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/past")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all future and current bike reservations for the user that sends the request.
     * @return the body of the response from the server.
     */
    public static String getAllFutureBikeReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/future")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all past bike reservations for the bike with the bikeId.
     * @param bikeId the id of the bike.
     * @return the body of the response from the server.
     */
    public static String getAllPreviousBikeReservationsForBike(int bikeId) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/pastAdmin?bikeId=" + bikeId)).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all future and current bike reservations for the bike with the bikeId.
     * @param bikeId the id of the bike.
     * @return the body of the response from the server.
     */
    public static String getAllFutureBikeReservationsForBike(int bikeId) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/futureAdmin?bikeId=" + bikeId)).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all active bike reservations for the user that sends the request.
     * @return the body of the response from the server.
     */
    public static String getAllActiveBikeReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/active")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Cancels a bike reservation for the user that sends the request.
     * @param id the id of the bike reservation.
     * @return the error message corresponding to the server's response.
     */
    public static String cancelBikeReservation(int id) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike_reservation/cancel/" + id)).build();
        return communicateAndReturnErrorMessage(request);
    }
}
