package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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

    @Test
    void roomMapper() {
        stubFor(get(urlEqualTo("/room/find/4")).willReturn(aResponse().withStatus(200).withBody("{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342}")));
        assertEquals(JsonMapper.roomMapper("{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342}"), JsonMapper.roomMapper((ServerCommunication.findRoom(4))));
    }

    @Test
    void roomListMapper() {
        List<Room> room = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomListMapper("[{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342},{\"id\":5,\"name\":\"1\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"1\",\"facultySpecific\":true,\"projector\":false,\"screen\":true,\"nrPeople\":1,\"plugs\":1}]\n")));
        stubFor(get(urlEqualTo("/room/all")).willReturn(aResponse().withStatus(200).withBody("[{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342},{\"id\":5,\"name\":\"1\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"1\",\"facultySpecific\":true,\"projector\":false,\"screen\":true,\"nrPeople\":1,\"plugs\":1}]\n")));
        assertEquals(room, JsonMapper.roomListMapper(ServerCommunication.getRooms()));
    }
}