package nl.tudelft.oopp.demo.communication;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Json;
import nl.tudelft.oopp.demo.entities.Building;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonMapperTest {
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
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }

    @Test
    void buildingMapper() {
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200).withBody("{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1}")));
        assertEquals(JsonMapper.buildingMapper("{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1}"), JsonMapper.buildingMapper(ServerCommunication.findBuilding(1)));
    }

    @Test
    void buildingListMapper() {
        stubFor(get(urlEqualTo("/building/all")).willReturn(aResponse().withStatus(200).withBody("[{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1},{\"id\":2,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},{\"id\":3,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},{\"id\":4,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1}]")));
        assertEquals(JsonMapper.buildingListMapper("[{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1},{\"id\":2,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},{\"id\":3,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},{\"id\":4,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1}]"), JsonMapper.buildingListMapper(ServerCommunication.getBuildings()));
    }
}