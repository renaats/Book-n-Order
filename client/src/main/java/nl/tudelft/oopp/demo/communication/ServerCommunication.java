package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a quote from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getQuote() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/quote")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Retrieves a user from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getUser() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/user")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Adds a building to the database.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String postBuilding() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/demo/add?name=EWI&email=ewi@tudelft.nl")).POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Retrieves all buildings from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getBuilding() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/demo/all")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Removes a building from the database.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String deleteBuilding() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/demo/delete?id=7")).POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }
}
