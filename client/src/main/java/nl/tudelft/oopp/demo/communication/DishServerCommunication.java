package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnBodyOfResponse;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnErrorMessage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import nl.tudelft.oopp.demo.authentication.AuthenticationKey;

/**
 * Controls all client to server communication related to the dish entity
 * Sends the appropriate HTTP request depending on the method
 */
public class DishServerCommunication {

    /**
     * Adds a dish to the database.
     * @param name dish name.
     * @param menuId menu id.
     * @return the error message corresponding to the server's response.
     */
    public static String addDish(String name, int menuId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/dish/add?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&menuId=" + menuId)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Add an allergy to a dish.
     * @param name allergy name.
     * @param id dish id.
     * @return the error message corresponding to the server's response.
     */
    public static String addAllergyToDish(String name, int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/dish/addAllergy?id=" + id + "&allergyName=" + URLEncoder.encode(name, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a dish on the server.
     * @param id = the id of the dish.
     * @param attribute = the attribute whose value is changed.
     * @param value = the new value of the attribute.
     * @return the body of the response from the server.
     */
    public static String updateDish(int id, String attribute, String value) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/dish/update?id=" + id + "&attribute=" + URLEncoder.encode(attribute, StandardCharsets.UTF_8) + "&value=" + URLEncoder.encode(value, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Queries the dishes on specific attributes.
     * @param query the query parameters.
     * @return A JSON list of rooms matching the query.
     */
    public static String filterDishes(String query) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/dish/filter?query=" + query)).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Deletes a dish from the database.
     * @param dishId the id of the dish.
     * @return the body of the response from the server.
     */
    public static String deleteDish(int dishId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/dish/delete/" + dishId)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Queries the allergies on specific attributes.
     * @param query the query parameters.
     * @return A JSON list of allergies matching the query.
     */
    public static String filterAllergies(String query) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/allergy/filter?query=" + query)).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Adds a food order to the database.
     * @param restaurantId restaurant id.
     * @param deliverLocation the location to deliver to.
     * @param deliverTimeMs the deliver time in milliseconds.
     * @return the body of the response from the server.
     */
    public static String addFoodOrder(int restaurantId, int deliverLocation, long deliverTimeMs) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/food_order/add?restaurantId=" + restaurantId + "&deliverLocation=" + deliverLocation + "&deliverTimeMs=" + deliverTimeMs)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a specific attribute of a food order.
     * @param id food order id.
     * @param attribute the attribute you want to change.
     * @param value the value of the change.
     * @return the body of the response from the server.
     */
    public static String updateFoodOrder(int id, String attribute, String value) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/food_order/update?id=" + id + "&attribute=" + URLEncoder.encode(attribute, StandardCharsets.UTF_8) + "&value=" + URLEncoder.encode(value, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Updates a specific attribute of a food order.
     * @param id food order id.
     * @param name the name of the dish you want to add.
     * @return the body of the response from the server.
     */
    public static String addDishToFoodOrder(int id, String name) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/food_order/addDish?id=" + id + "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Deletes a food order from the database.
     * @param id the id of the food order.
     * @return the body of the response from the server.
     */
    public static String deleteFoodOrder(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/food_order/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Gets all food orders from the database.
     * @return the body of the response from the server.
     */
    public static String getAllFoodOrders() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/food_order/all")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all previous food orders for the user that sends the request.
     * @return the body of the response from the server.
     */
    public static String getAllPreviousFoodOrders() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/food_order/past")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all future and actual food orders for the user that sends the request.
     * @return the body of the response from the server.
     */
    public static String getAllFutureFoodOrders() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/food_order/future")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds all future and actual food orders for the user that sends the request.
     * @return the body of the response from the server.
     */
    public static String getAllActiveFoodOrders() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/food_order/active")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Cancels a food order for the user that sends the request.
     * @param id food order id.
     * @return the error message corresponding to the server's response.
     */
    public static String cancelFoodOrder(int id) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/food_order/cancel/" + id)).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Adds a menu to the database.
     * @param name the name of the menu.
     * @param restaurantId the id of the restaurant for which the menu is applicable.
     * @return the body of the response from the server.
     */
    public static String addMenu(String name, int restaurantId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/menu/add?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&restaurantId=" + restaurantId)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes restaurant hours from the database.
     * @param id = the id of the restaurant.
     * @param day = the day of the week represented in an int.
     * @return the error message corresponding to the server's response.
     */
    public static String deleteRestaurantHours(int id, int day) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/delete?id=" + id + "&day=" + day)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Removes restaurant hours from the database.
     * @param id = the id of the restaurant.
     * @param date = the date in milliseconds or the day of the week for regular hours represented by long.
     * @return the error message corresponding to the server's response.
     */
    public static String deleteRestaurantHours(int id, long date) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/delete?id=" + id + "&date=" + date)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Deletes a menu from the database.
     * @param id the id of the menu.
     * @return the body of the response from the server.
     */
    public static String deleteMenu(int id) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/menu/delete/" + id)).DELETE().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Adds restaurant hours to the database.
     * @param restaurantId restaurant id.
     * @param date day represented by int.
     * @param startTimeS start time in seconds.
     * @param endTimeS end time in seconds.
     * @return the body of the response from the server.
     */
    public static String addRestaurantHours(int restaurantId, long date, int startTimeS, int endTimeS) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/add?restaurantId=" + restaurantId + "&date=" + date + "&startTimeS=" + startTimeS + "&endTimeS=" + endTimeS)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
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
     * @param day = the date in milliseconds or the day of the week for regular hours represented by long.
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
     * @return the error message corresponding to the server's response.
     */
    public static String updateRestaurantHours(int id, String attribute, String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant_hours/update?id=" + id + "&attribute=" + attribute + "&value=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Adds the user feedback for the restaurant.
     * @param restaurantId the id of the restaurant.
     * @param feedback if the feedback is positive (true), if it's negative (false).
     * @return the message corresponding to the server's response.
     */
    public static String addFoodFeedbackRestaurant(int restaurantId, boolean feedback) {
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant/addFeedback?id=" + restaurantId + "&feedback=" + feedback)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Sends a request to the server to add some feedback to a food order.
     * @param foodOrderId the id of the food order.
     * @param feedback if the feedback is positive true if the feedback is negative false.
     * @return the error message corresponding to the server's response.
     */
    public static String addFeedbackFoodOrder(int foodOrderId, boolean feedback) {
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/food_order/addFeedback?id=" + foodOrderId + "&feedback=" + feedback)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Sends a request to the server to retrieve the feedback of the given restaurant.
     * @param id the restaurant id the id of the food order.
     */
    public static String getFeedback(int id) {
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/restaurant/feedback?id=" + id)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return  (request.toString());
    }

}
