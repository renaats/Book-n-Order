package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

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
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            System.out.println("Status: " + response.statusCode());
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
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
            e.printStackTrace();
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