package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnBodyOfResponse;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.communicateAndReturnErrorMessage;

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
 * Controls all client to server communication related to the user entity
 * Sends the appropriate HTTP request depending on the method
 */
public class UserServerCommunication {
    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves the String representation of a user's own information from the server.
     * @return the body of the response from the server.
     */
    public static String getOwnUserInformation() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/info")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Changes the password of a given user.
     * @param changeValue = New value of the password.
     * @return the error message corresponding to the server's response.
     */
    public static String changeUserPassword(String changeValue) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/changePassword?password=" + URLEncoder.encode(changeValue, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves a boolean value from the server, false = no admin access, true = admin access.
     * @return a boolean representing whether the account should see the admin button.
     */
    public static boolean getAdminButtonPermission() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/admin")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return false;
        }
        return response.body().contains("true");
    }

    /**
     * Retrieves a boolean value from the server, false = no admin access, true = admin access.
     * @return a boolean representing whether the account should see the admin button.
     */
    public static boolean isUserAdmin() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/adminRole")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return false;
        }
        return response.body().contains("true");
    }

    /**
     * Retrieves a boolean value from the server, false = not activated, true = activated.
     * @return a boolean representing whether the account is activated.
     */
    public static boolean getAccountActivation() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user/activated")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return false;
        }
        return response.body().contains("true");
    }

    /**
     * Registers a user.
     * @param email User's email.
     * @param name User's name.
     * @param surname User's surname.
     * @param faculty User's faculty.
     * @param password User's password.
     * @param study User's study.
     * @return the error message corresponding to the server's response.
     */
    public static String addUser(String email, String name, String surname, String faculty, String password, String study) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/add?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)  + "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&surname=" + URLEncoder.encode(surname, StandardCharsets.UTF_8) + "&faculty=" + URLEncoder.encode(faculty, StandardCharsets.UTF_8) + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&study=" + URLEncoder.encode(study, StandardCharsets.UTF_8))).POST(HttpRequest.BodyPublishers.noBody()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Validates the six digit code of the user.
     * @param sixDigitCode The six digit code that the user inputs.
     * @return the error message corresponding to the server's response.
     */
    public static String validateUser(int sixDigitCode) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/validate?sixDigitCode=" + sixDigitCode)).POST(HttpRequest.BodyPublishers.noBody()).header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Authorizes the user.
     * @param email User's email.
     * @param password User's password.
     * @return the error message corresponding to the server's response.
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
                            ApplicationDisplay.changeScene("/MainMenu.fxml");
                        }
                        return ErrorMessages.getErrorMessage(200);
                    }
                }
            }
        } catch (IOException e) {
            //Left empty
        }
        return ErrorMessages.getErrorMessage(311);
    }

    /**
     * Logs out the user from their account.
     * @return the error message corresponding to the server's response.
     */
    public static String logoutUser() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/logout")).POST(HttpRequest.BodyPublishers.noBody()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Requests a new password for the user.
     * @param email User's email.
     * @return the error message corresponding to the server's response.
     */
    public static String sendRecoveryPassword(String email) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/user/recoverPassword?email="  + email)).POST(HttpRequest.BodyPublishers.noBody()).build();
        return communicateAndReturnErrorMessage(request);
    }

    /**
     * Retrieves the String representation of a user from the server.
     * @return the body of the response from the server.
     */
    public static String getUser() {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/user")).build();
        return communicateAndReturnBodyOfResponse(request);
    }

    /**
     * Finds user for a specific room reservation.
     * @param id = id of the room reservation.
     * @return the body of the response from the server.
     */
    public static String findUserForReservation(int id) {
        HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + AuthenticationKey.getBearerKey()).uri(URI.create("http://localhost:8080/room_reservation/user/" + id)).build();
        return communicateAndReturnBodyOfResponse(request);
    }
}
