package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ServerCommunicationTest {
    public WireMockServer wireMockServer;

    /**
     * Sets up the mock server before each test.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

    }

    /**
     * Tests the responses when the request is not authorized.
     * @throws IOException - should not be a problem
     */
    @Test
    public void testUnauthorized() throws IOException {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(post(urlEqualTo("/user/add?email=a&name=a&surname=a&faculty=a&password=a")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(delete(urlEqualTo("/building/delete/1")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(post(urlEqualTo("/building/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(delete(urlEqualTo("/room/delete/1")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(post(urlEqualTo("/room/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(post(urlEqualTo("/room/add?name=a&faculty=a&facultySpecific=true&screen=true&projector=true&buildingId=1&nrPeople=1&plugs=1"))
                .willReturn(aResponse().withStatus(403).withBody("")));
        stubFor(post(urlEqualTo("/building/add?name=a&street=a&houseNumber=1")).willReturn(aResponse().withStatus(403).withBody("")));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.getUser());
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.addUser("a", "a", "a", "a", "a"));
        assertEquals(ErrorMessages.getErrorMessage(311), ServerCommunication.loginUser("a", "a"));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.deleteBuilding(1));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.updateBuilding(1, "a", "a"));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.deleteRoom(1));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.updateRoom(1, "a", "a"));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.addRoom("a", "a", 1, true, true, true, 1, 1));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.addBuilding("a", "a", 1));
    }

    /**
     * Tests the responses when the request is successful.
     */
    @Test
    public void testSuccessful() throws UnsupportedEncodingException {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(200).withBody("Message1")));
        stubFor(post(urlEqualTo("/user/add?email=a&name=a&surname=a&faculty=a&password=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(200).withBody("").withHeader("Authorization", "a b c")));
        stubFor(get(urlEqualTo("/building/all")).willReturn(aResponse().withStatus(200).withBody("Message2")));
        stubFor(get(urlEqualTo("/room/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(delete(urlEqualTo("/building/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200).withBody("Message4")));
        stubFor(post(urlEqualTo("/building/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room/find/1")).willReturn(aResponse().withStatus(200).withBody("Message5")));
        stubFor(delete(urlEqualTo("/room/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/add?name=a&faculty=a&facultySpecific=true&screen=true&projector=true&buildingId=1&nrPeople=1&plugs=1"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/building/add?name=a&street=a&houseNumber=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        assertEquals("Message1", ServerCommunication.getUser());
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addUser("a", "a", "a", "a", "a"));
        assertEquals("Message2", ServerCommunication.getBuildings());
        assertEquals("Message3", ServerCommunication.getRooms());
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.deleteBuilding(1));
        assertEquals("Message4", ServerCommunication.findBuilding(1));
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateBuilding(1, "a", "a"));
        assertEquals("Message5", ServerCommunication.findRoom(1));
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.deleteRoom(1));
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateRoom(1, "a", "a"));
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addRoom("a", "a", 1, true, true, true, 1, 1));
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addBuilding("a", "a", 1));
    }

    /**
     * Tests the responses when the response is empty.
     */
    @Test
    public void testEmpty() {
        stubFor(get(urlEqualTo("/building/all")).willReturn(aResponse().withStatus(200).withBody("[]")));
        stubFor(get(urlEqualTo("/room/all")).willReturn(aResponse().withStatus(200).withBody("[]")));
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200).withBody("")));
        stubFor(get(urlEqualTo("/room/find/1")).willReturn(aResponse().withStatus(200).withBody("")));
        assertEquals(ErrorMessages.getErrorMessage(404), ServerCommunication.getBuildings());
        assertEquals(ErrorMessages.getErrorMessage(404), ServerCommunication.getRooms());
        assertEquals(ErrorMessages.getErrorMessage(404), ServerCommunication.findBuilding(1));
        assertEquals(ErrorMessages.getErrorMessage(404), ServerCommunication.findRoom(1));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}