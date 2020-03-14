package nl.tudelft.oopp.demo.errors;

public class ErrorMessages {
    private static final String addedMessage = "Successfully added!";
    private static final String notFoundMessage = "Not found.";
    private static final String unAuthorizedMessage = "You do not have the proper authorization.";
    private static final String preConditionFailedMessage = "Attribute does not exist!";
    private static final String IdNotFoundMessage = "No results on ID.";
    private static final String hasNoRoomsMessage = "Building has no rooms.";
    private static final String executedMessage = "Successfully executed.";
    private static final String alreadyReservedMessage = "Already reserved at this timeslot.";
    private static final String roomNotFoundMessage = "Room not found.";
    private static final String userNotFoundMessage = "User not found.";
    private static final String attributeNotFoundMessage = "Attribute not found.";
    private static final String reservationNotFoundMessage = "Reservation not found.";
    private static final String buildingNotFoundMessage = "Building not found.";
    private static final String nameAlreadyExistsMessage = "Name already exists.";
    private static final String emailAlreadyExistsMessage = "Email already exists.";
    private static final String improperLoginCredentialsMessage = "Login and/or password is incorrect.";
    private static final String invalidEmailMessage = "You have to input a valid email.";
    private static final String invalidEmailDomainMessage = "A TU Delft email is required. To create a restaurant account please contact an admin.";
    private static final String invalidDayMessage = "Invalid day.";
    private static final String endBeforeStartMessage = "End time cannot be before start time.";
    private static final String duplicateBuildingDayMessage = "This building already has a time for this day.";
    private static final String restaurantNotFoundMessage = "Restaurant not found.";
    private static final String somethingWentWrongMessage = "Something went wrong.";
    private static int code;

    /**
     * Checks which errorCode has been given and returns the corresponding message.
     * These error codes are related to HTTP Status Codes.
     *.............
     * 1xx: Informational. Communicates transfer protocol-level information.
     * 2xx: Success. Indicates that the client’s request was accepted successfully.
     * 3xx: Redirection. Indicates that the client must take some additional action in order to complete their request.
     * 4xx: Client Error. This category of error status codes points the finger at clients.
     * 5xx: Server Error. The server takes responsibility for these error status codes.
     *.............
     * Moreover, none of the alreadt existing HTTP Status Codes have been used see https://restfulapi.net/http-status-codes/ for reference.
     *
     * @param code error code
     * @return message, string
     */
    public static String getErrorMessage(int code) {
        switch (code) {
            case 200:
                return executedMessage;
            case 201:
                return addedMessage;
            case 308:
                return alreadyReservedMessage;
            case 309:
                return nameAlreadyExistsMessage;
            case 310:
                return emailAlreadyExistsMessage;
            case 311:
                return improperLoginCredentialsMessage;
            case 401:
                return unAuthorizedMessage;
            case 404:
                return notFoundMessage;
            case 412:
                return preConditionFailedMessage;
            case 416:
                return IdNotFoundMessage;
            case 417:
                return hasNoRoomsMessage;
            case 418:
                return roomNotFoundMessage;
            case 419:
                return userNotFoundMessage;
            case 420:
                return attributeNotFoundMessage;
            case 421:
                return reservationNotFoundMessage;
            case 422:
                return buildingNotFoundMessage;
            case 423:
                return invalidEmailMessage;
            case 424:
                return invalidEmailDomainMessage;
            case 425:
                return invalidDayMessage;
            case 426:
                return endBeforeStartMessage;
            case 427:
                return duplicateBuildingDayMessage;
            case 428:
                return restaurantNotFoundMessage;
            default:
                return somethingWentWrongMessage;
        }
    }
}
