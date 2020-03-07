package nl.tudelft.oopp.demo.errors;

public class ErrorMessages {
    private static final String addedMessage = "Succesfully added!";
    private static final String notFoundMessage = "Not found.";
    private static final String unAuthorizedMessage = "You do not have the proper authorization.";
    private static final String preConditionFailedMessage = "Attribute does not exist!";
    private static final String IdNotFoundMessage = "No results on ID.";
    private static final String hasNoRoomsMessage = "Building has no rooms.";
    private static final String executedMessage = "Succesfully executed.";
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
            default:
                return notFoundMessage;
        }
    }
}
