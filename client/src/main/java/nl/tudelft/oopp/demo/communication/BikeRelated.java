package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnBodyOfResponse;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnErrorMessage;

import java.net.URI;
import java.net.http.HttpRequest;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;

/**
 * Controls all client to server communication related to the bike entity
 * Sends the appropriate HTTP request depending on the method
 */
public class BikeRelated {

    /**
     * gets all bike reservations from the database.
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
