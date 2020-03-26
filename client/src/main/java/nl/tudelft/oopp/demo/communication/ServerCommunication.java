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

    // ---------------------------------------------
    // Building related Server Communication methods
    // ---------------------------------------------

    /**
     * Retrieves all buildings from the server.
     * @return the body of the response from the server.
     */
    public static String getBuildings() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/building/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Adds building hours to the server
     * @param buildingId Building id
     * @param date Day represented by int
     * @param startTimeS Start time in seconds
     * @param endTimeS End time in seconds
     * @return response.body of the server
     */
    public static String deleteBuilding(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a building in the database by id.
     * @param buildingID = building id, which is parsed from a text field.
     * @return the body of the response.
     */
    public static String findBuilding(int buildingID) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/find/" + buildingID)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Communicates the buildings to add to the database
     * @param name building name
     * @param street street name
     * @param houseNumber house number
     * @return response body
     */
    public static String addBuilding(String name, String street,int houseNumber) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/add?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&street=" + URLEncoder.encode(street, StandardCharsets.UTF_8) + "&houseNumber=" + houseNumber)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
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
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Adds building hours to the server
     * @param buildingId Building id
     * @param day Day represented by int
     * @param startTimeS Start time in seconds
     * @param endTimeS End time in seconds
     * @return response.body of the server
     */
    public static String addBuildingHours(int buildingId, long date, int startTimeS, int endTimeS) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/add?buildingId=" + buildingId + "&date=" + date + "&startTimeS=" + startTimeS + "&endTimeS=" + endTimeS)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Adds restaurant hours to the server
     * @param restaurantId Restaurant id
     * @param day Day represented by int
     * @param startTimeS Start time in seconds
     * @param endTimeS End time in seconds
     * @return response.body of the server
     */
    public static String addRestaurantHours(int restaurantId, long date, int startTimeS, int endTimeS) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/add?restaurantId=" + restaurantId + "&date=" + date + "&startTimeS=" + startTimeS + "&endTimeS=" + endTimeS)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all restaurant hours from the server.
     * @return the body of the response from the server.
     */
    public static String getRestaurantHours() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/restaurant_hours/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieve specific restaurant opening hours for specific day in the database by id.
     * @param restaurantId = restaurant id, which is parsed from a text field.
     * @param day = the date in milliseconds or the day of the week for regular hours represented by long
     * @return the body of the response.
     */
    public static String findRestaurantHours(int restaurantId, long day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/find/" + restaurantId + "/" + day)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Updates a given attribute of restaurant hours.
     * @param id = id of the restaurant hour to be updated.
     * @param attribute = The attribute whose value is to be updated.
     * @param changeValue = New value.
     * @return The body of the response from the server.
     */
    public static String updateRestaurantHours(int id, String attribute, String changeValue) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        HttpResponse<String> response;
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes restaurant hours from the database.
     * @param id = the id of the restaurant.
     * @param date the date in milliseconds or the day of the week for regular hours represented by long.
     * @return the body of the response from the server.
     */
    public static String deleteRestaurantHours(int id, long date) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/delete?id=" + id + "&date=" + date)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    // -----------------------------------------
    // User related Server Communication Methods
    // -----------------------------------------

    /**
     * Retrieves the String representation of a user's own information from the server.
     * @return the body of the response from the server.
     */
    public static String getOwnUserInformation() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/info")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a boolean value from the server, false = no admin access, true = admin access.
     * @return the body of the response from the server.
     */
    public static boolean getAdminButtonPermission() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/admin")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return response.body().contains("true");
    }

    /**
     * Retrieves a boolean value from the server, false = not activated, true = activated.
     * @return the body of the response from the server.
     */
    public static boolean getAccountActivation() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/activated")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return response.body().contains("true");
    }

    /**
     * Registers a user.
     * @param email User's email
     * @param name User's name
     * @param surname User's surname
     * @param password User's password
     * @return the body of a get request to the server.
     */
    public static String addUser(String email, String name, String surname, String faculty, String password) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/add?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)  + "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&surname=" + URLEncoder.encode(surname, StandardCharsets.UTF_8) + "&faculty=" + URLEncoder.encode(faculty, StandardCharsets.UTF_8) + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Validates the six digit code of the user.
     * @param sixDigitCode The six digit code that the user inputs
     * @return  The error message corresponding to the response of the server
     */
    public static String validateUser(int sixDigitCode) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/validate?sixDigitCode=" + sixDigitCode)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Authorizes the user.
     * @param email User's email
     * @param password User's password
     * @return the body of a get request to the server.
     */
    public static String loginUser(String email, String password) {
        try {
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
                        AuthenticationKey.setBearerKey((Arrays.asList(entry.getValue().get(0).split(" ")).get(1)));
                        if (!getAccountActivation()) {
                            ApplicationDisplay.changeScene("/ConfirmationSixDigits.fxml");
                        } else {
                            ApplicationDisplay.changeScene("/mainMenu.fxml");
                        }
                        return ErrorMessages.getErrorMessage(200);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ErrorMessages.getErrorMessage(311);
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
     * Requests a new password for the user.
     * @param email User's email.
     * @return the body of the response from the server.
     */
    public static String getBuildingHours() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/building_hours/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves all restaurant hours from the server.
     * @return the body of the response from the server.
     */
    public static String getRestaurantHours() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/restaurant_hours/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieves a JSON string representation of all rooms from the server.
     * @return the body of the response from the server.
     */
    public static String getRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Removes a building from the database.
     * @param id Id of the building to be removed.
     * @return the body of the response from the server.
     */
    public static String deleteBuilding(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a building in the database by id.
     * @param buildingID Building id, which is parsed from a text field.
     * @return the body of the response.
     */
    public static String findBuilding(int buildingID) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/find/" + buildingID)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieve specific building hours for specific day in the database by id.
     * @param buildingId Building id, which is parsed from a text field.
     * @param day Int representation for the day of the week
     * @return the body of the response.
     */
    public static String findBuildingHours(int buildingId, int day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/find/" + buildingId + "/" + day)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieve specific restaurant opening hours for specific day in the database by id.
     * @param restaurantId Restaurant id, which is parsed from a text field.
     * @param day Int representation for the day of the week
     * @return the body of the response.
     */
    public static String findRestaurantHours(int restaurantId, int day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/find/" + restaurantId + "/" + day)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Updates a given attribute of building.
     * @param id Id of the building to be updated.
     * @param attribute Attribute whose value is to be updated.
     * @param changeValue New value.
     * @return The body of the response from the server.
     */
    public static String updateBike(int id, String attribute, String changeValue) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        HttpResponse<String> response;
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of building hours.
     * @param id Id of the building hour to be updated.
     * @param attribute Attribute whose value is to be updated.
     * @param changeValue New value.
     * @return The body of the response from the server.
     * */
    public static String updateBuildingHours(int id, String attribute, String changeValue) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of restaurant hours.
     * @param id Id of the restaurant hour to be updated.
     * @param attribute Attribute whose value is to be updated.
     * @param changeValue New value.
     * @return The body of the response from the server.
     */
    public static String addBike(int buildingId, boolean available) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" + available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        HttpResponse<String> response;
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a room by given id.
     * @param roomId Id of the room.
     * @return The body of the response from the server.
     */
    public static String findRoom(int roomId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room/find/" + roomId)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Removes a room from the database.
     * @param id Id of the room.
     * @return the body of the response from the server.
     */
    public static String deleteBike(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes building hours from the database.
     * @param id Id of the building.
     * @param day Day of the week represented in an int.
     * @return the body of the response from the server.
     */
    public static String getBikes() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Removes restaurant hours from the database.
     * @param id Id of the restaurant.
     * @param day Day of the week represented in an int.
     * @return the body of the response from the server.
     */
    public static String deleteRestaurantHours(int id, int day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/delete?id=" + id + "&day=" + day)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of a room.
     * @param id Id of the room.
     * @param attribute Attribute whose value is to be changed.
     * @param changeValue New value.
     * @return the body of the response from the server.
     */
    public static String updateBike(int id, String attribute, String changeValue) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        HttpResponse<String> response;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates addRoom to the database
     * @param name Room name
     * @param faculty Faculty name
     * @param buildingId Building ID
     * @param facultySpecific Is it specific for a faculty
     * @param screen Does the room have a screen
     * @param projector Does the room have a projector
     * @param capacity Capacity of the room in people
     * @param plugs Amount of available plugs
     * @return body response
     */
    public static String addBike(int buildingId, boolean available) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" +available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        HttpResponse<String> response;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" +available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates the buildings to add to the database
     * @param name Building name
     * @param street Street name
     * @param houseNumber House number
     * @return response body
     */
    public static String deleteBike(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Asks the server for all bikes in the database
     * @return a Json string with all the bikes in the database
     */
    public static String getBikes() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Communicates addRoomReservation to the database
     * @param roomId Id of the room
     * @param userEmail user email
     * @param fromTimeMs start time of the reservation in milliseconds
     * @param toTimeMs end time of the reservation in milliseconds
     * @return Body response
     */
    public static String addRoomReservation(int roomId, String userEmail, long fromTimeMs, long toTimeMs) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/add?roomId=" + roomId + "&userEmail=" + userEmail + "&fromTimeMs=" + fromTimeMs + "&toTimeMs=" + toTimeMs)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of a room reservation.
     * @param id The id of the room reservation.
     * @param attribute The attribute whose value is to be changed.
     * @param changeValue New value.
     * @return the body of the response from the server.
     */
    public static String updateRoomReservation(int id, String attribute, String changeValue) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes a room reservation from the database.
     * @param id The id of the room reservation.
     * @return the body of the response from the server.
     */
    public static String deleteRoomReservation(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/room_reservation/delete?id=" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of a bike.
     * @param id = the id of the bike.
     * @param attribute = The attribute whose value is to be changed.
     * @param changeValue = New value.
     * @return the body of the response from the server.
     */
    public static String updateBike(int id, String attribute, String changeValue) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        HttpResponse<String> response;
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates addBike to the database
     * @return body response
     */
    public static String addBike(int buildingId, boolean available) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" + available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        HttpResponse<String> response;
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
     * Asks the server for all bikes in the database
     * @return a Json string with all the bikes in the database
     */
    public static String getBikes() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike/all")).build();
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
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        HttpResponse<String> response;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates addBike to the database
     * @return body response
     */
    public static String addBike(int buildingId, boolean available) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" +available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        HttpResponse<String> response;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" +available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
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
     * Asks the server for all bikes in the database
     * @return a Json string with all the bikes in the database
     */
    public static String getBikes() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike/all")).build();
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
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, "UTF-8"))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Please enter an encoding that is supported by the URLEncode class.";
        }
        HttpResponse<String> response;
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Communicates addBike to the database
     * @return body response
     */
    public static String addBike(int buildingId, boolean available) {
        HttpRequest request;
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/bike/add?buildingId=" + buildingId + "&available=" + available)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        HttpResponse<String> response;
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
     * Asks the server for all bikes in the database
     * @return a Json string with all the bikes in the database
     */
    public static String getBikes() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/bike/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }
}