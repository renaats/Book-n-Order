package nl.tudelft.oopp.demo.errors;

/**
 * Creates the appropriate error messages when a problem occurs.
 */
public class ErrorMessages {
    private static final String addedMessage = "Successfully added!";
    private static final String confirmEmailMessage = "Successfully added. Please confirm this account via the link sent to your email.";
    private static final String recoverPasswordMessage = "You will shortly receive an email to recover your account!";
    private static final String notFoundMessage = "Not found.";
    private static final String unAuthorizedMessage = "You do not have the proper authorization.";
    private static final String preConditionFailedMessage = "Attribute does not exist!";
    private static final String IdNotFoundMessage = "No results on ID.";
    private static final String hasRoomsMessage = "Building has rooms.";
    private static final String executedMessage = "Successfully executed.";
    private static final String codesDoNotMatchMessage = "The six digit code does not match with the one sent in the email. Try again.";
    private static final String alreadyReservedMessage = "Already reserved at this time slot.";
    private static final String roomNotFoundMessage = "Room not found.";
    private static final String userNotFoundMessage = "User not found.";
    private static final String wrongUserMessage = "This reservation was not made by this user.";
    private static final String reservationNotFoundMessage = "Reservation not found.";
    private static final String allBikesReservedMessage = "No bikes available.";
    private static final String orderNotFoundMessage = "Order not found.";
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
    private static final String menuAlreadyExistsMessage = "Already exists.";
    private static final String unsupportedEncodingMessage = "Please enter an encoding that is supported by the URLEncode class.";
    private static final String somethingWentWrongMessage = "Something went wrong.";
    private static final String menuNotFoundMessage = "Menu not found.";
    private static final String dishNotFoundMessage = "Dish not found.";
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
     * Moreover, none of the already existing HTTP Status Codes have been used see https://restfulapi.net/http-status-codes/ for reference.
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
            case 203:
                return confirmEmailMessage;
            case 205:
                return recoverPasswordMessage;
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
            case 414:
                return allBikesReservedMessage;
            case 415:
                return orderNotFoundMessage;
            case 416:
                return IdNotFoundMessage;
            case 417:
                return hasRoomsMessage;
            case 418:
                return roomNotFoundMessage;
            case 419:
                return userNotFoundMessage;
            case 420:
                return wrongUserMessage;
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
            case 429:
                return menuNotFoundMessage;
            case 430:
                return dishNotFoundMessage;
            case 431:
                return codesDoNotMatchMessage;
            case 432:
                return menuAlreadyExistsMessage;
            case 502:
                return unsupportedEncodingMessage;
            default:
                return somethingWentWrongMessage;
        }
    }
}
