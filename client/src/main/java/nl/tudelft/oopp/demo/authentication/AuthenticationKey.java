package nl.tudelft.oopp.demo.authentication;

/**
 * Manages the AuthenticationKey for server communication
 */
public class AuthenticationKey {
    private static String bearerKey;

    public static String getBearerKey() {
        return bearerKey;
    }

    public static void setBearerKey(String bearerKey) {
        AuthenticationKey.bearerKey = bearerKey;
    }
}
