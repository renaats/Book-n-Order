package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;

import nl.tudelft.oopp.demo.errors.ErrorMessages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the BikeRelated class by using a mock server to simulate client to server communication.
 */
class BikeServerCommunicationTest {
    public WireMockServer wireMockServer;

    /**
     * Sets up the mock server before each test.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        stubFor(get(urlEqualTo("/bike_reservation/all")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/future")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/past")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/cancel/1")).willReturn(aResponse().withStatus(200).withBody("201")));
        stubFor(get(urlEqualTo("/bike_reservation/active")).willReturn(aResponse().withStatus(200).withBody("Message")));
    }

    /**
     * Tests getting all bike reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllBikeReservations() {
        assertEquals("200", BikeServerCommunication.getAllBikeReservations());
    }

    /**
     * Tests getting all future bike reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureBikeReservations() {
        assertEquals("200", BikeServerCommunication.getAllFutureBikeReservations());
    }

    /**
     * Tests getting all previous bike reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllPastBikeReservations() {
        assertEquals("200", BikeServerCommunication.getAllPreviousBikeReservations());
    }

    /**
     * Tests the response when the cancelBikeReservation request is successful.
     */
    @Test
    public void testCancelBikeReservation() {
        assertEquals(ErrorMessages.getErrorMessage(201), BikeServerCommunication.cancelBikeReservation(1));
    }

    /**
     * Tests the response when the testGetAllActiveBikeReservations request is successful.
     */
    @Test
    public void testGetAllActiveBikeReservations() {
        assertEquals("Message", BikeServerCommunication.getAllActiveBikeReservations());
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}