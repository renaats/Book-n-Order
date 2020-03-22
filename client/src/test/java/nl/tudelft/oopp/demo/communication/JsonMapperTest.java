package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.tudelft.oopp.demo.entities.Room;
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
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200)
                .withBody("{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1}")));
        assertEquals(JsonMapper
                        .buildingMapper("{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1}"),
                JsonMapper.buildingMapper(ServerCommunication.findBuilding(1)));
    }

    @Test
    void buildingListMapper() {
        stubFor(get(urlEqualTo("/building/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1},"
                                + "{\"id\":2,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},"
                                + "{\"id\":3,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},"
                                + "{\"id\":4,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1}]")));
        assertEquals(
                JsonMapper
                        .buildingListMapper("[{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1},"
                                + "{\"id\":2,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},"
                                + "{\"id\":3,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1},"
                                + "{\"id\":4,\"name\":\"1\",\"street\":\"1\",\"houseNumber\":1}]"),
                JsonMapper.buildingListMapper(ServerCommunication.getBuildings()));
    }

    @Test
    void roomMapper() {
        stubFor(get(urlEqualTo("/room/find/4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":"
                                + "\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342}")));
        assertEquals(
                JsonMapper
                        .roomMapper(
                                "{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":"
                                        + "\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342}"),
                JsonMapper.roomMapper((ServerCommunication.findRoom(4))));
    }

    @Test
    void roomListMapper() {
        List<Room> room =
                new ArrayList<>(Objects
                        .requireNonNull(JsonMapper
                                .roomListMapper("[{\"id\":4,\"name\":\"432\","
                                        + "\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"42342\","
                                        + "\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234,\"plugs\":42342},"
                                        + "{\"id\":5,\"name\":\"1\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},"
                                        + "\"faculty\":\"1\",\"facultySpecific\":true,\"projector\":false,\"screen\":true,"
                                        + "\"nrPeople\":1,\"plugs\":1}]\n")));
        stubFor(get(urlEqualTo("/room/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1}"
                                + ",\"faculty\":\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"nrPeople\":4234"
                                + ","
                                + "\"plugs\":42342},{\"id\":5,\"name\":\"1\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":"
                                + "1}"
                                + ",\"faculty\":\"1\",\"facultySpecific\":true,\"projector\":false,\"screen\":true,\"nrPeople\":1,\"plugs\":1}]\n")));
        assertEquals(room, JsonMapper.roomListMapper(ServerCommunication.getRooms()));
    }

//    @Test
//    void foodMapper() {
//        stubFor(get(urlEqualTo("/food/find/7"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withBody("{\"id\":7,\"restaurant\":{\"restaurant\":{\"id\":4,\"building\":"
//                                + "{\"id\":1,\"name\":\"test\",\"street\":\"1\",\"houseNumber\":1},"
//                                + "\"name\":\"TestRestaurant\",\"menu\":null},\"appUser\":{\"email\":"
//                                + "\"a.delia@student.tudelft.nl\",\"password\":\"abc\",\"name\":\"Alto\","
//                                + "\"surname\":\"Delia\",\"faculty\":\"EWI\"loggedIn\"true\",\"roles\":{"
//                                + "\"id\":\"2\",\"name\":\"ROLE_ADMIN\"}},\"deliveryLocation\":{\"id\"1\"name\":"
//                                + "\"street\",\"street\":\"str\",\"houseNumber\":\"24\"},\"deliveryTime\":\"2020-03-25T12:00:00.000+0000}]")));
//        assertEquals(
//                JsonMapper
//                        .foodOrderMapper(
//                                "{\"id\":7,\"restaurant\":{\"restaurant\":{\"id\":4,\"building\":"
//                                        + "{\"id\":1,\"name\":\"test\",\"street\":\"1\",\"houseNumber\":1},"
//                                        + "\"name\":\"TestRestaurant\",\"menu\":null},\"appUser\":{\"email\":"
//                                        + "\"a.delia@student.tudelft.nl\",\"password\":\"abc\",\"name\":\"Alto\","
//                                        + "\"surname\":\"Delia\",\"faculty\":\"EWI\"loggedIn\"true\",\"roles\":{"
//                                        + "\"id\":\"2\",\"name\":\"ROLE_ADMIN\"}},\"deliveryLocation\":{\"id\"1\"name\":"
//                                        + "\"street\",\"street\":\"str\",\"houseNumber\":\"24\"},\"deliveryTime\":\"2020-03-25T12:00:00.000+0000}]"),
//                JsonMapper.roomMapper((ServerCommunication.findFoodOrder(7))));
//    }
}