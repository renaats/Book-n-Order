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
 * Tests the BuildingRelated class by using a mock server to simulate client to server communication.
 */
class BuildingServerCommunicationTest {
    public WireMockServer wireMockServer;

    /**
     * Sets up the mock server before each test.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        stubFor(get(urlEqualTo("/building/all")).willReturn(aResponse().withStatus(200).withBody("Message2")));
        stubFor(delete(urlEqualTo("/building/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200).withBody("Message4")));
        stubFor(post(urlEqualTo("/building/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/building/add?name=a&street=a&houseNumber=1&faculty=faculty"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building/findName/name")).willReturn(aResponse().withStatus(200).withBody("Message")));
        stubFor(post(urlEqualTo("/building_hours/add?buildingId=1&date=1&startTimeS=1&endTimeS=2"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building_hours/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(get(urlEqualTo("/building_hours/find/1/1"))
                .willReturn(aResponse().withStatus(200).withBody("Message5")));
        stubFor(post(urlEqualTo("/building_hours/update?id=1&attribute=attribute&value=value"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/building_hours/add?buildingId=1&date=1&startTimeS=1&endTimeS=2"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building_hours/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(get(urlEqualTo("/building_hours/find/1/1")).willReturn(aResponse().withStatus(200).withBody("Message5")));
        stubFor(post(urlEqualTo("/building_hours/update?id=1&attribute=attribute&value=value"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/building_hours/delete?id=1&day=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/building_hours/delete?id=1&date=1")).willReturn(aResponse().withStatus(200).withBody("200")));
    }

    /**
     * Tests the response when the getBuildings request is successful.
     */
    @Test
    public void testSuccessfulGetBuildings() {
        assertEquals("Message2", BuildingServerCommunication.getBuildings());
    }

    /**
     * Tests the response when the deleteBuilding request is successful.
     */
    @Test
    public void testSuccessfulDeleteBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingServerCommunication.deleteBuilding(1));
    }

    /**
     * Tests the response when the findBuilding request is successful.
     */
    @Test
    public void testSuccessfulFindBuilding() {
        assertEquals("Message4", BuildingServerCommunication.findBuilding(1));
    }

    /**
     * Tests the response when the updateBuilding request is successful.
     */
    @Test
    public void testSuccessfulUpdateBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingServerCommunication.updateBuilding(1, "a", "a"));
    }

    /**
     * Tests the response when the addBuilding request is successful.
     */
    @Test
    public void testSuccessfulAddBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingServerCommunication.addBuilding("a", "a", 1, "faculty"));
    }

    /**
     * Tests the response when the findBuildingByName request is successful.
     */
    @Test
    public void testSuccessfulFindBuildingByName() {
        assertEquals("Message", BuildingServerCommunication.findBuildingByName("name"));
    }

    /**
     * Tests the response when the addBuildingHours request is successful.
     */
    @Test
    public void testSuccessfulAddBuildingHours() {
        assertEquals("Successfully executed.", BuildingServerCommunication.addBuildingHours(1, 1L, 1, 2));
    }

    /**
     * Tests the response when the getBuildingHours request is successful.
     */
    @Test
    public void testSuccessfulGetBuildingHours() {
        assertEquals("Message3", BuildingServerCommunication.getBuildingHours());
    }

    /**
     * Tests the response when the findBuildingHours request is successful.
     */
    @Test
    public void testSuccessfulFindBuildingHours() {
        assertEquals("Message5", BuildingServerCommunication.findBuildingHours(1,1));
    }

    /**
     * Tests the response when the updateBuildingHours request is successful.
     */
    @Test
    public void testSuccessfulUpdateBuildingHours() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingServerCommunication.updateBuildingHours(1,"attribute", "value"));
    }

    /**
     * Tests the response when the deleteBuildingHours by day request is successful.
     */
    @Test
    public void testSuccessfulDeleteBuildingHoursByDay() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingServerCommunication.deleteBuildingHours(1, 1));
    }

    /**
     * Tests the response when the deleteBuildingHours by date request is successful.
     */
    @Test
    public void testSuccessfulDeleteBuildingHoursByDate() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingServerCommunication.deleteBuildingHours(1, 1L));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}