package nl.tudelft.oopp.demo.user;

public class UserInformation {
    private static String bearerKey;

    public static String getBearerKey() {
        return bearerKey;
    }

    public static void setBearerKey(String bearerKey) {
        UserInformation.bearerKey = bearerKey;
    }
}
