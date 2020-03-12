package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.user.UserInformation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class ServerCommunication {

    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves the String representation of a user from the server.
     * @return the body of the response from the server.
     */
    public static String getUser() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/user")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
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
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Authorizes the user.
     * @param email User's email
     * @param password User's password
     * @return the body of a get request to the server.
     */
    public static String loginUser(String email, String password) throws IOException {

        URL url = new URL("http://localhost:8080/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = "{\"email\": \"" + email + "\", \"password\":\"" + password + "\"}";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        Map<String, List<String>> map = connection.getHeaderFields();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                if (entry.getKey().equals("Authorization")) {
                    // Yes it's gross, it works, it grabs the key
                    UserInformation.setBearerKey((Arrays.asList(entry.getValue().get(0).split(" ")).get(1)));
                    ApplicationDisplay.changeScene("/myCurrentBookings.fxml");
                    return ErrorMessages.getErrorMessage(200);
                }
            }
        }
        return ErrorMessages.getErrorMessage(311);
    }

    /**
     * Retrieves all buildings from the server.
     * @return the body of a get request to the server.
     */
    public static String getBuildings() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/building/all")).build();
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
        if (response.body().equals("[]")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            return response.body();
        }
    }

    /**
     * Retrieves a String representation of all buildings from the server.
     * @return the body of the response from the server.
     */
    public static String getRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/room/all")).build();
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
        if (response.body().equals("[]")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            return response.body();
        }
    }

    /**
     * Removes a building from the database.
     * @param id = id of the building to be removed.
     * @return the body of the response from the server.
     */
    public static String deleteBuilding(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/delete/" + id)).DELETE().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Retrieves a building in the database by id.
     * @param buildingID = building id, which is parsed from a text field.
     * @return the body of the response.
     */
    public static String findBuilding(int buildingID) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/find/" + buildingID)).GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
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
     * Updates a given attribute of building.
     * @param id = id of the building to be updated.
     * @param attribute = The attribute whose value is to be updated.
     * @param changeValue = New value.
     * @return The body of the response from the server.
     */
    public static String updateBuilding(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/update?id=" + id + "&attribute=" + attribute + "&value=" + changeValue)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Retrieves a room by given id.
     * @param roomId = the id of the room.
     * @return The body of the response from the server.
     */
    public static String findRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/find/" + roomId)).GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
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
     * Removes a room from the database.
     * @param id = the id of the room.
     * @return the body of the response from the server.
     */
    public static String deleteRoom(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/delete/" + id)).DELETE().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Updates a given attribute of a room.
     * @param id = the id of the room.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the body of the response from the server.
     */
    public static String updateRoom(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/update?id=" + id + "&attribute=" + attribute + "&value=" + changeValue)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
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
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/add?name=" + name + "&faculty=" + faculty + "&facultySpecific=" + facultySpecific + "&screen=" + screen + "&projector=" + projector + "&buildingId=" + buildingId + "&nrPeople=" + capacity + "&plugs=" + plugs)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Communicates the buildings to add to the database
     * @param name building name
     * @param street street name
     * @param houseNumber house number
     * @return response body
     */
    public static String addBuilding(String name, String street, int houseNumber) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/add?name=" + name + "&street=" + street + "&houseNumber=" + houseNumber)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }

    /**
     * Should log the user out
     * @return confirmation message
     */
    public static String logoutUser() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/logout")).POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() == 403) {
            return ErrorMessages.getErrorMessage(401);
        }
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        return ErrorMessages.getErrorMessage(Integer.parseInt(response.body()));
    }
}