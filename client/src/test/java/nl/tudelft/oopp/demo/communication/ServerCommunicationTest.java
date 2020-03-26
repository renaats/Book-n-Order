package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import java.io.IOException;

import nl.tudelft.oopp.demo.errors.ErrorMessages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ServerCommunication class by using a mock server to simulate client to server communication.
 */
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

        stubFor(post(urlEqualTo("/user/add?email=a&name=a&surname=a&faculty=a&password=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(200).withBody("").withHeader("Authorization", "a b c")));
        stubFor(get(urlEqualTo("/building/all")).willReturn(aResponse().withStatus(200).withBody("Message2")));
        stubFor(get(urlEqualTo("/bike/all")).willReturn(aResponse().withStatus(200).withBody("bikes")));
        stubFor(get(urlEqualTo("/room/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(delete(urlEqualTo("/building/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/bike/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200).withBody("Message4")));
        stubFor(post(urlEqualTo("/building/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/bike/update?id=1&attribute=available&value=false")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room/find/1")).willReturn(aResponse().withStatus(200).withBody("Message5")));
        stubFor(delete(urlEqualTo("/room/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/bike/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/add?name=a&faculty=a&facultySpecific=true&screen=true&projector=true&buildingId=1&capacity=1&plugs=1"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/bike/add?buildingId=1&available=true")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/building/add?name=a&street=a&houseNumber=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room_reservation/add?roomId=1&userEmail=a&fromTimeMs=1&toTimeMs=2"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/all")).willReturn(aResponse().withStatus(200).withBody("RoomReservations")));
        stubFor(post(urlEqualTo("/room_reservation/update?id=1&attribute=a&value=b")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/room_reservation/delete?id=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/user/info")).willReturn(aResponse().withStatus(200).withBody("Information")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(200).withBody("token")));
        stubFor(get(urlEqualTo("/room_reservation/find/10")).willReturn(aResponse().withStatus(200).withBody("Message10")));
    }

    /**
     * Tests the response when the server does not respond to the request.
     */
    @Test
    public void testServerNotResponding() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
        assertEquals("Communication with server failed", ServerCommunication.getUser());
    }

    /**
     * Tests the response when the server returns an empty object.
     */
    @Test
    public void testEmptyUser() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(200).withBody("[]")));
        assertEquals(ErrorMessages.getErrorMessage(404), ServerCommunication.getUser());
    }

    /**
     * Tests the response when the getUser request is not authorized.
     */
    @Test
    public void testUnauthorizedGetUser() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(403).withBody("")));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.getUser());
    }

    /**
     * Tests the response when the getUser request is successful.
     */
    @Test
    public void testSuccessfulGetUser() {
        stubFor(get(urlEqualTo("/user")).willReturn(aResponse().withStatus(200).withBody("Message1")));
        assertEquals("Message1", ServerCommunication.getUser());
    }

    /**
     * Tests the response when the addUser request is successful.
     */
    @Test
    public void testSuccessfulAddUser() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addUser("a", "a", "a", "a", "a"));
    }

    /**
     * Tests the response when the getBuildings request is successful.
     */
    @Test
    public void testSuccessfulGetBuildings() {
        assertEquals("Message2", ServerCommunication.getBuildings());
    }

    /**
     * Tests the response when the getRooms request is successful.
     */
    @Test
    public void testSuccessfulGetRooms() {
        assertEquals("Message3", ServerCommunication.getRooms());
    }

    /**
     * Tests the response when the deleteBuilding request is successful.
     */
    @Test
    public void testSuccessfulDeleteBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.deleteBuilding(1));
    }

    /**
     * Tests the response when the findBuilding request is successful.
     */
    @Test
    public void testSuccessfulFindBuilding() {
        assertEquals("Message4", ServerCommunication.findBuilding(1));
    }

    /**
     * Tests the response when the updateBuilding request is successful.
     */
    @Test
    public void testSuccessfulUpdateBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateBuilding(1, "a", "a"));
    }

    /**
     * Tests the response when the findRoom request is successful.
     */
    @Test
    public void testSuccessfulFindRoom() {
        assertEquals("Message5", ServerCommunication.findRoom(1));
    }

    /**
     * Tests the response when the deleteRoom request is successful.
     */
    @Test
    public void testSuccessfulDeleteRoom() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.deleteRoom(1));
    }

    /**
     * Tests the response when the updateRoom request is successful.
     */
    @Test
    public void testSuccessfulUpdateRoom() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateRoom(1, "a", "a"));
    }

    /**
     * Tests the response when the addRoom request is successful.
     */
    @Test
    public void testSuccessfulAddRoom() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addRoom("a", "a", 1, true, true, true, 1, 1));
    }

    /**
     * Tests the response when the addBuilding request is successful.
     */
    @Test
    public void testSuccessfulAddBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addBuilding("a", "a", 1));
    }

    /**
     * Tests the response when the addBike request is successful.
     */
    @Test
    public void testSuccessfulAddBike() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addBike(1, true));
    }

    /**
     * Tests the response when the updateBike request is successful.
     */
    @Test
    public void testSuccessfulUpdateBike() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateBike(1,"available", "false"));
    }

    /**
     * Tests the response when the deleteBike request is successful.
     */
    @Test
    public void testSuccessfulDeleteBike() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.deleteBike(1));
    }

    /**
     * Tests the response when the getBikes request is successful.
     */
    @Test
    public void testSuccessfulGetBikes() {
        assertEquals("bikes", ServerCommunication.getBikes());
    }

    /**
     * Tests the response when the logoutUser request is successful.
     */
    @Test
    public void testSuccessfulLogoutUser() {
        stubFor(post(urlEqualTo("/user/logout")).willReturn(aResponse().withStatus(200).withBody("200")));
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.logoutUser());
    }

    /**
     * Tests the response when the logoutUser request is unsuccessful.
     */
    @Test
    public void testUnsuccessfulLogoutUser() {
        stubFor(post(urlEqualTo("/user/logout")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER).withBody("200")));
        assertEquals("Communication with server failed", ServerCommunication.logoutUser());
    }

    /**
     * Tests the response when the logoutUser request is unauthorized.
     */
    @Test
    public void testUnauthorizedLogoutUser() {
        stubFor(post(urlEqualTo("/user/logout")).willReturn(aResponse().withStatus(403).withBody("200")));
        assertEquals(ErrorMessages.getErrorMessage(401), ServerCommunication.logoutUser());
    }

    /**
     * Tests the response when the findRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulFindRoomReservation() {
        assertEquals("Message10", ServerCommunication.findRoomReservation(10));
    }

    /**
     * Tests the response when the addRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulAddRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addRoomReservation(1, "a", 1, 2));
    }

    /**
     * Tests the response when the getRoomReservations request is successful.
     */
    @Test
    public void testSuccessfulGetRoomReservations() {
        assertEquals("RoomReservations", ServerCommunication.getRoomReservations());
    }

    /**
     * Tests the response when the updateRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulUpdateRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateRoomReservation(1, "a","b"));
    }

    /**
     * Tests the response when the deleteRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulDeleteRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.deleteRoomReservation(1));
    }

    /**
     * Tests the response when the getOwnUserInformation request is successful.
     */
    @Test
    public void testSuccessfulGetOwnUserInformation() {
        assertEquals("Information", ServerCommunication.getOwnUserInformation());
    }

    /**
     * Tests the response when the getAdminButtonPermission request is successful.
     */
    @Test
    public void testSuccessfulGetAdminButtonPermission() {
        stubFor(get(urlEqualTo("/user/admin")).willReturn(aResponse().withStatus(200).withBody("true")));
        assertTrue(ServerCommunication.getAdminButtonPermission());
    }

    /**
     * Tests the response when the getAdminButtonPermission request is unsuccessful.
     */
    @Test
    public void testUnsuccessfulGetAdminButtonPermission() {
        stubFor(get(urlEqualTo("/user/admin")).willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER).withBody("true")));
        assertFalse(ServerCommunication.getAdminButtonPermission());
    }

    /**
     * Tests the response when the loginUser request is successful.
     */
    @Test
    public void testSuccessfulLoginUser() {
        assertEquals(ErrorMessages.getErrorMessage(311), ServerCommunication.loginUser("a", "b"));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}