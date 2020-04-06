package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import nl.tudelft.oopp.demo.errors.ErrorMessages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the UserRelated class by using a mock server to simulate client to server communication.
 */
class UserServerCommunicationTest {
    public WireMockServer wireMockServer;

    /**
     * Sets up the mock server before each test.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        stubFor(post(urlEqualTo("/user/add?email=a&name=a&surname=a&faculty=a&password=a&study=a"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(200).withBody("").withHeader("Authorization", "a b c")));
        stubFor(post(urlEqualTo("/user/changePassword?password=Password")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/user/info")).willReturn(aResponse().withStatus(200).withBody("Information")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(200).withBody("token")));
    }

    /**
     * Tests the response when the server does not respond to the request.
     */
    @Test
    public void testServerNotResponding() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
        assertEquals("Communication with server failed", UserServerCommunication.getUser());
    }

    /**
     * Tests the response when the server returns an empty object.
     */
    @Test
    public void testEmptyUser() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(200).withBody("[]")));
        assertEquals(ErrorMessages.getErrorMessage(404), UserServerCommunication.getUser());
    }

    /**
     * Tests the response when the getUser request is not authorized.
     */
    @Test
    public void testUnauthorizedGetUser() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(403).withBody("")));
        assertEquals(ErrorMessages.getErrorMessage(401), UserServerCommunication.getUser());
    }

    /**
     * Tests the response when the validate request is successful.
     */
    @Test
    public void testSuccessfulValidateUser() {
        stubFor(post(urlEqualTo("/user/validate?sixDigitCode=123456")).willReturn(aResponse().withStatus(200).withBody("200")));
        assertEquals(ErrorMessages.getErrorMessage(200), UserServerCommunication.validateUser(123456));
    }

    /**
     * Tests the response when the getAccountActivation request is successful.
     */
    @Test
    public void testSuccessfulGetAccountActivation() {
        stubFor(get(urlEqualTo("/user/activated")).willReturn(aResponse().withStatus(200).withBody("true")));
        assertTrue(UserServerCommunication.getAccountActivation());
    }

    /**
     * Tests the response when the getAccountActivation request is unsuccessful.
     */
    @Test
    public void testUnsuccessfulGetAccountActivation() {
        stubFor(get(urlEqualTo("/user/activated")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER).withBody("true")));
        assertFalse(UserServerCommunication.getAccountActivation());
    }

    /**
     * Tests the response when the getUser request is successful.
     */
    @Test
    public void testSuccessfulGetUser() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(200).withBody("Message1")));
        assertEquals("Message1", UserServerCommunication.getUser());
    }

    /**
     * Tests the response when the addUser request is successful.
     */
    @Test
    public void testSuccessfulAddUser() {
        assertEquals(ErrorMessages.getErrorMessage(200), UserServerCommunication.addUser("a", "a", "a", "a", "a", "a"));
    }

    /**
     * Tests the response when the logoutUser request is successful.
     */
    @Test
    public void testSuccessfulLogoutUser() {
        stubFor(post(urlEqualTo("/user/logout")).willReturn(aResponse().withStatus(200).withBody("200")));
        assertEquals(ErrorMessages.getErrorMessage(200), UserServerCommunication.logoutUser());
    }

    /**
     * Tests the response when the logoutUser request is unsuccessful.
     */
    @Test
    public void testUnsuccessfulLogoutUser() {
        stubFor(post(urlEqualTo("/user/logout")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER).withBody("200")));
        assertEquals("Communication with server failed", UserServerCommunication.logoutUser());
    }

    /**
     * Tests the response when the logoutUser request is unauthorized.
     */
    @Test
    public void testUnauthorizedLogoutUser() {
        stubFor(post(urlEqualTo("/user/logout")).willReturn(aResponse().withStatus(403).withBody("200")));
        assertEquals(ErrorMessages.getErrorMessage(401), UserServerCommunication.logoutUser());
    }

    /**
     * Tests the response when the getOwnUserInformation request is successful.
     */
    @Test
    public void testSuccessfulGetOwnUserInformation() {
        assertEquals("Information", UserServerCommunication.getOwnUserInformation());
    }

    /**
     * Tests the response when the getAdminButtonPermission request is successful.
     */
    @Test
    public void testSuccessfulGetAdminButtonPermission() {
        stubFor(get(urlEqualTo("/user/admin")).willReturn(aResponse().withStatus(200).withBody("true")));
        assertTrue(UserServerCommunication.getAdminButtonPermission());
    }

    /**
     * Tests the response when the getAdminButtonPermission request is unsuccessful.
     */
    @Test
    public void testUnsuccessfulGetAdminButtonPermission() {
        stubFor(get(urlEqualTo("/user/admin")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER).withBody("true")));
        assertFalse(UserServerCommunication.getAdminButtonPermission());
    }

    /**
     * Tests the response when the loginUser request is successful.
     */
    @Test
    public void testSuccessfulLoginUser() {
        assertEquals(ErrorMessages.getErrorMessage(311), UserServerCommunication.loginUser("a", "b"));
    }

    /**
     * Tests the response when the loginUser request is successful.
     */
    @Test
    public void testSuccessfulChangePassword() {
        assertEquals(ErrorMessages.getErrorMessage(200), UserServerCommunication.changeUserPassword("Password"));
    }

    /**
     * Tests the response when the logoutUser request is successful.
     */
    @Test
    public void testSuccessfulSendRecoveryPassword() {
        stubFor(post(urlEqualTo("/user/recoverPassword?email=test@tudelft.nl")).willReturn(aResponse().withStatus(200).withBody("200")));
        assertEquals(ErrorMessages.getErrorMessage(200), UserServerCommunication.sendRecoveryPassword("test@tudelft.nl"));
    }

    @Test
    public void testGetUserForReservation() {
        stubFor(get(urlEqualTo("/room_reservation/user/1")).willReturn(aResponse().withStatus(200).withBody("Message51")));
        assertEquals("Message51", UserServerCommunication.findUserForReservation(1));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}