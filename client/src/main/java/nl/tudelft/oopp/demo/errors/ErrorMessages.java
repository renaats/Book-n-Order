package nl.tudelft.oopp.demo.errors;

public class ErrorMessages {
    private static final String addedMessage = "Succesfully added!";
    private static final String notFoundMessage = "Not found.";
    private static final String unAuthorizedMessage = "You do not have the proper authorization.";
    private static final String preConditionFailedMessage = "Attribute does not exist!";
    private static final String IdNotFoundMessage = "No results on ID.";
    private static final String hasNoRoomsMessage = "Building has no rooms.";
    private static final String executedMessage = "Succesfully executed.";
    private static final String alreadyReservedMessage = "Already reserved at this timeslot.";
    private static final String roomNotFoundMessage = "Room not found.";
    private static final String userNotFoundMessage = "User not found.";
    private static final String attributeNotFoundMessage = "Attribute not found.";
    private static final String reservationNotFoundMessage = "Reservation not found.";
    private static final String buildingNotFoundMessage = "Building not found.";
    private static final String nameAlreadyExistsMessage = "Name already exists.";
    private static final String emailAlreadyExistsMessage = "Email already exists.";
    private static int code;

    /**
     * Checks which errorCode has been given and returns the corresponding message.
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
            default:
                return notFoundMessage;
        }
    }
}
