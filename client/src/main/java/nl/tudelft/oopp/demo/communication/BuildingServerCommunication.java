package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnBodyOfResponse;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnErrorMessage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;

/**
 * Controls all client to server communication related to the building entity
 * Sends the appropriate HTTP request depending on the method
 */
public class BuildingServerCommunication {

    /**
     * Retrieves all buildings from the server.
     * @return the body of the response from the server.
     */
    public static String getBuildings() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/building/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Removes a building from the database.
     * @param id = id of the building to be removed.
     * @return the error message corresponding to the server's response.
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
     * Finds a building in the database by name.
     * @param name String of the name.
     * @return the body of the response.
     */
    public static String findBuildingByName(String name) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/findName/" + URLEncoder.encode(name, StandardCharsets.UTF_8))).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Communicates the buildings to add to the database.
     * @param name building name.
     * @param street street name.
     * @param houseNumber house number.
     * @param faculty the faculty of the building if applicable.
     * @return the error message corresponding to the server's response.
     */
    public static String addBuilding(String name, String street, int houseNumber, String faculty) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/add?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&street=" + URLEncoder.encode(street, StandardCharsets.UTF_8) + "&houseNumber=" + houseNumber + "&faculty=" + URLEncoder.encode(faculty, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a given attribute of building.
     * @param id = id of the building to be updated.
     * @param attribute = The attribute whose value is to be updated.
     * @param changeValue = New value.
     * @return the error message corresponding to the server's response.
     */
    public static String updateBuilding(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Adds building hours to the server.
     * @param buildingId building id.
     * @param date day represented by int.
     * @param startTimeS start time in seconds.
     * @param endTimeS end time in seconds.
     * @return the body of the response from the server.
     */
    public static String addBuildingHours(int buildingId, long date, int startTimeS, int endTimeS) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/add?buildingId=" + buildingId + "&date=" + date + "&startTimeS=" + startTimeS + "&endTimeS=" + endTimeS)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves all building_hours from the server.
     * @return the body of the response from the server.
     */
    public static String getBuildingHours() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/building_hours/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieve specific building hours for a specific day in the database by id.
     * @param buildingId = building id, which is parsed from a text field.
     * @param day = the date in milliseconds or the day of the week for regular hours represented by long.
     * @return the body of the response.
     */
    public static String findBuildingHours(int buildingId, long day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/find/" + buildingId + "/" + day)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Retrieve specific building hours for a specific day in the database by id.
     * @param buildingId = building id, which is parsed from a text field.
     * @param day = the day in integer representation (1 - 7)
     * @return the body of the response.
     */
    public static String findBuildingHoursByDay(int buildingId, int day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/findAdmin/" + buildingId + "/" + day)).GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Updates a given attribute of building hours.
     * @param id = id of the building hour to be updated.
     * @param attribute = The attribute whose value is to be updated.
     * @param changeValue = New value.
     * @return the error message corresponding to the server's response.
     */
    public static String updateBuildingHours(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes building hours from the database.
     * @param id = the id of the building.
     * @param day = the day of the week represented in an int.
     * @return the error message corresponding to the server's response.
     */
    public static String deleteBuildingHours(int id, int day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/delete?id=" + id + "&day=" + day)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes building hours from the database.
     * @param id = the id of the building.
     * @param date = the date in milliseconds or the day of the week for regular hours represented by long.
     * @return the error message corresponding to the server's response.
     */
    public static String deleteBuildingHours(int id, long date) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/building_hours/delete?id=" + id + "&date=" + date)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }
}
