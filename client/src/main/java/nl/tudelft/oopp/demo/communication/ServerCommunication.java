package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import nl.tudelft.oopp.demo.errors.ErrorMessages;

public class ServerCommunication {

    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a user from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getUser() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/user")).build();
        HttpResponse<String> response;
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
     * @param email User's email
     * @param name User's name
     * @param surname User's surname
     * @param password User's password
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String addUser(String email, String name, String surname, String faculty, String password) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/add?email=" + email + "&name=" + name + "&surname=" + surname + "&faculty=" + faculty + "&password=" + password)).POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Retrieves a user from the server.
     * @param email User's email
     * @param name User's name
     * @param surname User's surname
     * @param password User's password
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String loginUser(String email, String name, String surname, String password) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/user/add?email=" + email + "&name=" + name + "&surname=" + surname + "&password=" + password)).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Retrieves all buildings from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getBuildings() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/building/all")).build();
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
        if (response.body().equals("[]")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            return response.body();
        }
    }

    /**
     * Retrieves all buildings from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/room/all")).build();
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
        if (response.body().equals("[]")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            return response.body();
        }
    }

    /**
     * Removes a building from the database.
     *
     * @param id = building id
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String deleteBuilding(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/delete/" + id)).DELETE().build();
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
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Finds a building in the database by id
     *
     * @param buildingID = building id, which is parsed from a text field
     * @return the body of the response
     */
    public static String findBuilding(int buildingID) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/find/" + buildingID)).GET().build();
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
        if (response.body().equals("")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            System.out.println(response.body());
            return response.body();
        }
    }


    /**
     * Updates a building
     * @param id = Id of the building
     * @param attribute = Attribute from a choicebox, always right / proper input!
     * @param changeValue = Value they want to change, is not checked, can be wrong!
     * @return
     */
    public static String updateBuilding(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/update?id=" + id + "&attribute=" + attribute + "&value=" + changeValue)).POST(HttpRequest.BodyPublishers.noBody()).build();
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
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Finds a room
     * @param roomId the id of the room
     * @return body of the message
     */
    public static String findRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/find/" + roomId)).GET().build();
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
        if (response.body().equals("")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            System.out.println(response.body());
            return response.body();
        }
    }

    /**
     * Removes a building from the database.
     * @param id = building id
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String deleteRoom(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/delete/" + id)).DELETE().build();
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
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Updates a building
     * @param id = Id of the building
     * @param attribute = Attribute from a choicebox, always right / proper input!
     * @param changeValue = Value they want to change, is not checked, can be wrong!
     * @return
     */
    public static String updateRoom(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/update?id=" + id + "&attribute=" + attribute + "&value=" + changeValue)).POST(HttpRequest.BodyPublishers.noBody()).build();
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
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Communicates addRoom to the database
     * @param name room name
     * @param faculty faculty name
     * @param buildingId building ID
     * @param facultySpecific is it specific for a faculty
     * @param screen does the room have a screen
     * @param projector does the room have a projector
     * @param capacity capacity of the room in people
     * @param plugs amount of available plugs
     * @return body response
     */
    public static String addRoom(String name, String faculty,
                                 int buildingId, boolean facultySpecific,
                                 boolean screen, boolean projector,
                                 int capacity, int plugs) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/add?name=" + name + "&faculty=" + faculty + "&facultySpecific=" + facultySpecific + "&screen=" + screen + "&projector=" + projector + "&buildingId=" + buildingId + "&nrPeople=" + capacity + "&plugs=" + plugs)).POST(HttpRequest.BodyPublishers.noBody()).build();
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