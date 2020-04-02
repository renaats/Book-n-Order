package nl.tudelft.oopp.demo.communication;

import com.github.tomakehurst.wiremock.WireMockServer;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the BuildingRelated class by using a mock server to simulate client to server communication.
 */
class BuildingRelatedTest {
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
    }

    /**
     * Tests the response when the getBuildings request is successful.
     */
    @Test
    public void testSuccessfulGetBuildings() {
        assertEquals("Message2", BuildingRelated.getBuildings());
    }

    /**
     * Tests the response when the deleteBuilding request is successful.
     */
    @Test
    public void testSuccessfulDeleteBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingRelated.deleteBuilding(1));
    }

    /**
     * Tests the response when the findBuilding request is successful.
     */
    @Test
    public void testSuccessfulFindBuilding() {
        assertEquals("Message4", BuildingRelated.findBuilding(1));
    }

    /**
     * Tests the response when the updateBuilding request is successful.
     */
    @Test
    public void testSuccessfulUpdateBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingRelated.updateBuilding(1, "a", "a"));
    }

    /**
     * Tests the response when the addBuilding request is successful.
     */
    @Test
    public void testSuccessfulAddBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), BuildingRelated.addBuilding("a", "a", 1, "faculty"));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}