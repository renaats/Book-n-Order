package nl.tudelft.oopp.demo.communication;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import nl.tudelft.oopp.demo.errors.ErrorMessages;

/**
 * Controls all client to server communication
 * Sends the appropriate HTTP request depending on the method
 */
public class ServerCommunication {
    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Handles the request/response process between client/server.
     * @param request HttpRequest that was made.
     * @return the error message corresponding to the server's response.
     */
    public static String communicateAndReturnErrorMessage(HttpRequest request) {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return "Communication with server failed";
        }
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            System.out.println("Status: " + response.statusCode());
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (Integer.parseInt(response.body()) >= 1000) {
            return response.body();
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Handles the request/response process between client/server.
     * @param request HttpRequest that was made.
     * @return the body of the response from the server.
     */
    public static String communicateAndReturnBodyOfResponse(HttpRequest request) {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return "Communication with server failed";
        }
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            System.out.println("Status: " + response.statusCode());
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.body().equals("[]") || response.body().equals("")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            return response.body();
        }
    }
}