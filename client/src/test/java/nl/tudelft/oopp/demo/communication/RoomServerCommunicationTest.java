package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;

import nl.tudelft.oopp.demo.errors.ErrorMessages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the RoomRelated class by using a mock server to simulate client to server communication.
 */
class RoomServerCommunicationTest {
    public WireMockServer wireMockServer;

    /**
     * Sets up the mock server before each test.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        stubFor(get(urlEqualTo("/room/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(get(urlEqualTo("/room/find/1")).willReturn(aResponse().withStatus(200).withBody("Message5")));
        stubFor(delete(urlEqualTo("/room/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/add?name=a&studySpecific=a&screen=true&projector=true&buildingId=1&capacity=1&plugs=1&status=A"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room_reservation/add?roomId=1&fromTimeMs=1&toTimeMs=2"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/all")).willReturn(aResponse().withStatus(200).withBody("RoomReservations")));
        stubFor(post(urlEqualTo("/room_reservation/update?id=1&attribute=a&value=b")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/room_reservation/delete?id=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/find/10")).willReturn(aResponse().withStatus(200).withBody("Message10")));
        stubFor(get(urlEqualTo("/room_reservation/cancel/1")).willReturn(aResponse().withStatus(200).withBody("201")));
        stubFor(get(urlEqualTo("/room/filter?query=name%3AAuditorium")).willReturn(aResponse().withStatus(200).withBody("Message15")));
        stubFor(get(urlEqualTo("/room_reservation/past")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/future")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/active")).willReturn(aResponse().withStatus(200).withBody("Message4")));
        stubFor(get(urlEqualTo("/room_reservation/room/1")).willReturn(aResponse().withStatus(200).withBody("Message50")));
    }

    /**
     * Tests getting all future room reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureRoomReservations() {
        assertEquals("200", RoomServerCommunication.getAllFutureRoomReservations());
    }

    /**
     * Tests getting all active room reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllActiveRoomReservations() {
        assertEquals("Message4", RoomServerCommunication.getAllActiveRoomReservations());
    }

    /**
     * Tests getting all previous room reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllPastRoomReservations() {
        assertEquals("200", RoomServerCommunication.getAllPreviousRoomReservations());
    }

    /**
     * Tests the response when the getRooms request is successful.
     */
    @Test
    public void testSuccessfulGetRooms() {
        assertEquals("Message3", RoomServerCommunication.getRooms());
    }

    /**
     * Tests the response when the findRoom request is successful.
     */
    @Test
    public void testSuccessfulFindRoom() {
        assertEquals("Message5", RoomServerCommunication.findRoom(1));
    }

    /**
     * Tests the response when the deleteRoom request is successful.
     */
    @Test
    public void testSuccessfulDeleteRoom() {
        assertEquals(ErrorMessages.getErrorMessage(200), RoomServerCommunication.deleteRoom(1));
    }

    /**
     * Tests the response when the updateRoom request is successful.
     */
    @Test
    public void testSuccessfulUpdateRoom() {
        assertEquals(ErrorMessages.getErrorMessage(200), RoomServerCommunication.updateRoom(1, "a", "a"));
    }

    /**
     * Tests the response when the addRoom request is successful.
     */
    @Test
    public void testSuccessfulAddRoom() {
        assertEquals(ErrorMessages.getErrorMessage(200), RoomServerCommunication.addRoom("a", 1, "a", true, true, 1, 1, "A"));
    }

    /**
     * Tests the response when the filterRooms request is successful.
     */
    @Test
    public void testSuccessfulFilterRooms() {
        assertEquals("Message15", RoomServerCommunication.filterRooms("name:Auditorium"));
    }

    /**
     * Tests the response when the findRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulFindRoomReservation() {
        assertEquals("Message10", RoomServerCommunication.findRoomReservation(10));
    }

    /**
     * Tests the response when the addRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulAddRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(200), RoomServerCommunication.addRoomReservation(1, 1, 2));
    }

    /**
     * Tests the response when the getRoomReservations request is successful.
     */
    @Test
    public void testSuccessfulGetRoomReservations() {
        assertEquals("RoomReservations", RoomServerCommunication.getRoomReservations());
    }

    /**
     * Tests the response when the updateRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulUpdateRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(200), RoomServerCommunication.updateRoomReservation(1, "a", "b"));
    }

    /**
     * Tests the response when the deleteRoomReservation request is successful.
     */
    @Test
    public void testSuccessfulDeleteRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(200), RoomServerCommunication.deleteRoomReservation(1));
    }

    /**
     * Tests the response when the cancelRoomReservation request is successful.
     */
    @Test
    public void testCancelRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(201), RoomServerCommunication.cancelRoomReservation(1));
    }

    @Test
    public void testFindReservationForRoom() {
        assertEquals("Message50", RoomServerCommunication.findReservationForRoom(1));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}