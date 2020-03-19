package nl.tudelft.oopp.demo.user;

/**
 * Gets and sets the bearerKey for a user.
 */
public class UserInformation {
    private static String bearerKey;

    public static String getBearerKey() {
        return bearerKey;
    }

    public static void setBearerKey(String bearerKey) {
        UserInformation.bearerKey = bearerKey;
    }
}
