package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nl.tudelft.oopp.demo.errors.ErrorMessages;
import nl.tudelft.oopp.demo.user.UserInformation;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class ServerCommunication {

    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Handles the request/response process between client/server.
     * @param request HttpRequest that was made
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
     * Handles the request/response process between client/server.
     * @param request HttpRequest that was made
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
        if (response.statusCode() != 200) {
            System.out.println(response.body());
            System.out.println("Status: " + response.statusCode());
        }
        if (response.body().equals("[]") || response.body().equals("")) {
            return ErrorMessages.getErrorMessage(404);
        } else {
            return response.body();
        }
    }

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
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/add?email=" + URLEncoder.encode(email,"UTF-8")  + "&name=" + URLEncoder.encode(name,"UTF-8") + "&surname=" + URLEncoder.encode(surname,"UTF-8") + "&faculty=" + URLEncoder.encode(faculty, "UTF-8") + "&password=" + URLEncoder.encode(password,"UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
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
                    ApplicationDisplay.changeScene("/mainMenu.fxml");
                    return ErrorMessages.getErrorMessage(200);
                }
            }
        }
        return ErrorMessages.getErrorMessage(311);
    }

    /**
     * Retrieves all buildings from the server.
     * @return the body of the response from the server.
     */
    public static String getBuildings() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/building/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a JSON string representation of all rooms from the server.
     * @return the body of the response from the server.
     */
    public static String getRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/room/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Removes a building from the database.
     * @param id = id of the building to be removed.
     * @return the body of the response from the server.
     */
    public static String deleteBuilding(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/delete/" + id)).DELETE().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a building in the database by id.
     * @param buildingID = building id, which is parsed from a text field.
     * @return the body of the response.
     */
    public static String findBuilding(int buildingID) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/find/" + buildingID)).GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }


    /**
     * Updates a given attribute of building.
     * @param id = id of the building to be updated.
     * @param attribute = The attribute whose value is to be updated.
     * @param changeValue = New value.
     * @return The body of the response from the server.
     */
    public static String updateBuilding(int id, String attribute, String changeValue) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a room by given id.
     * @param roomId = the id of the room.
     * @return The body of the response from the server.
     */
    public static String findRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/find/" + roomId)).GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Removes a room from the database.
     * @param id = the id of the room.
     * @return the body of the response from the server.
     */
    public static String deleteRoom(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/delete/" + id)).DELETE().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of a room.
     * @param id = the id of the room.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the body of the response from the server.
     */
    public static String updateRoom(int id, String attribute, String changeValue) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
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
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/add?name=" + URLEncoder.encode(name, "UTF-8") + "&faculty=" + URLEncoder.encode(faculty, "UTF-8") + "&facultySpecific=" + facultySpecific + "&screen=" + screen + "&projector=" + projector + "&buildingId=" + buildingId + "&capacity=" + capacity + "&plugs=" + plugs)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates the buildings to add to the database
     * @param name building name
     * @param street street name
     * @param houseNumber house number
     * @return response body
     */
    public static String addBuilding(String name, String street, int houseNumber) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/add?name=" + URLEncoder.encode(name, "UTF-8") + "&street=" + URLEncoder.encode(street, "UTF-8") + "&houseNumber=" + houseNumber)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Should log the user out
     * @return confirmation message
     */
    public static String logoutUser() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/logout")).POST(HttpRequest.BodyPublishers.noBody()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves all room reservations from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getRoomReservations() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
    /**
     * Retrieves a room reservation by given id.
     * @param roomReservationId = the id of the room reservation.
     * @return The body of the response from the server.
     */
    public static String findRoomReservation(int roomReservationId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/find/" + roomReservationId)).GET().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Communicates addRoomReservation to the database
     * @param room name of the room
     * @param buildingId building ID
     * @param userId user ID
     * @param from start date and time of the reservation
     * @param to end date and time of the reservation
     * @return body response
     */
    public static String addRoomReservation(String room, int buildingId, int userId, Date from, Date to) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/add?room" + URLEncoder.encode(room, "UTF-8") + "&buildingId=" + buildingId + "&userId=" + userId + "&from=" + from + "&to=" + to)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of a room reservation.
     * @param id = the id of the room reservation.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the body of the response from the server.
     */
    public static String updateRoomReservation(int id, String attribute, String changeValue) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes a room reservation from the database.
     * @param id = the id of the room reservation.
     * @return the body of the response from the server.
     */
    public static String deleteRoomReservation(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/delete/" + id)).DELETE().header("Authorization", "Bearer " + UserInformation.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }
}