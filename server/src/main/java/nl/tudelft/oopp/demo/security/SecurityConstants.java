package nl.tudelft.oopp.demo.security;

/**
 * Stores some pre-defined security constants.
 */
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/add";
    public static final String PASSWORD_RECOVER_URL = "/user/recoverPassword";
}
