package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the JsonMapper class
 */
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
    void buildingListMapper() throws IOException {
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
                                + "\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"capacity\":4234,\"plugs\":42342}")));
        assertEquals(
                JsonMapper
                        .roomMapper(
                                "{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":"
                                + "\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"capacity\":4234,\"plugs\":42342}"),
                JsonMapper.roomMapper((ServerCommunication.findRoom(4))));
    }

    @Test
    void roomListMapper() {
        List<Room> room =
                new ArrayList<>(Objects
                        .requireNonNull(JsonMapper
                                .roomListMapper("[{\"id\":4,\"name\":\"432\","
                                        + "\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},\"faculty\":\"42342\","
                                        + "\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"capacity\":4234,\"plugs\":42342},"
                                        + "{\"id\":5,\"name\":\"1\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1},"
                                        + "\"faculty\":\"1\",\"facultySpecific\":true,\"projector\":false,\"screen\":true,"
                                        + "\"capacity\":1,\"plugs\":1}]\n")));
        stubFor(get(urlEqualTo("/room/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":4,\"name\":\"432\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":1}"
                                + ",\"faculty\":\"42342\",\"facultySpecific\":true,\"projector\":false,\"screen\":false,\"capacity\":4234"
                                + ","
                                + "\"plugs\":42342},{\"id\":5,\"name\":\"1\",\"building\":{\"id\":1,\"name\":\"11\",\"street\":\"1\",\"houseNumber\":"
                                + "1}"
                                + ",\"faculty\":\"1\",\"facultySpecific\":true,\"projector\":false,\"screen\":true,\"capacity\":1,\"plugs\":1}]\n")));
        assertEquals(room, JsonMapper.roomListMapper(ServerCommunication.getRooms()));
    }

    @Test
    void buildingHoursMapperTest() {
        stubFor(get(urlEqualTo("/building_hours/find/1/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":3,\"day\":1,\"building\":{\"id\":1,\"name\":\"test\","
                                + "\"street\":\"1\",\"houseNumber\":1},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}")));
        String json = "{\"id\":3,\"day\":1,\"building\":{\"id\":1,\"name\":\"test\""
                + ",\"street\":\"1\",\"houseNumber\":1},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}";

        BuildingHours buildingHours = JsonMapper.buildingHoursMapper(json);

        assertEquals(buildingHours, JsonMapper.buildingHoursMapper(ServerCommunication.findBuildingHours(1, 1)));
    }

    @Test
    void restaurantHoursMapperTest() {
        stubFor(get(urlEqualTo("/restaurant_hours/find/1/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":5,\"day\":1,\"restaurant\":{\"id\":4,\"building\":"
                                + "{\"id\":1,\"name\":\"test\",\"street\":\"1\",\"houseNumber\":1},"
                                + "\"name\":\"TestRestaurant\",\"menu\":null},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}")));

        String json = "{\"id\":5,\"day\":1,\"restaurant\":{\"id\":4,\"building\":{\"id\":1,\"name\":"
                + "\"test\",\"street\":\"1\",\"houseNumber\":1},\"name\":\"TestRestaurant\","
                + "\"menu\":null},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}";

        RestaurantHours restaurantHours = JsonMapper.restaurantHoursMapper(json);

        assertEquals(restaurantHours, JsonMapper.restaurantHoursMapper(ServerCommunication.findRestaurantHours(1, 1)));
    }

//    @Test
//    void RoomReservationMapperTest() {
//        stubFor(get(urlEqualTo("/room_reservation/find/3"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withBody("[{\"id\":3,\"room\":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},\""
//                                + "faculty\":\"b\",\"facultySpecific\":true,\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\":\""
//                                + "r.jursevskis@student.tudelft.nl\",\"password\":\"abc\",\"name\":\"Renats\""
//                                + ",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":183937,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\""
//                                + "id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\",\"toTime\":\"2020-03-19T12:00:00.000+0000\"}]\n")));
//
//        String json = "[{\"id\":3,\"room\":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},\""
//                + "faculty\":\"b\",\"facultySpecific\":true,\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\":\""
//                + "r.jursevskis@student.tudelft.nl\",\"password\":\"abc\",\"name\":\"Renats\""
//                + ",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":183937,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\""
//                + "id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\",\"toTime\":\"2020-03-19T12:00:00.000+0000\"}]\n";
//
//        RoomReservation roomReservation = JsonMapper.roomReservationMapper(json);
//
//        assertEquals(roomReservation, JsonMapper.roomReservationMapper(ServerCommunication.findRoomReservation(3)));
//    }
//
//    @Test
//    void RoomReservationListMapperTest() {
//        stubFor(get(urlEqualTo("/room_reservation/all"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withBody("[{\"id\":3,\"room\":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},"
//                                + "\"faculty\":\"b\",\"facultySpecific\":true,\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\""
//                                + ":\"r.jursevskis@student.tudelft.nl\",\"password\":\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\",\"name\":\"Renats"
//                                + "\",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\""
//                                + "},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\",\"toTime\":\"2020-03-19T12:00:00.000+0000\"},{\"id\":4,\"room\""
//                                + ":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},\"faculty\":\"b\",\"facultySpecific\":true,"
//                                + "\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\":\"r.jursevskis@student.tudelft.nl\",\"password\":"
//                                + "\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\",\"name\":\"Renats\",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,"
//                                + "\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"1970-01-01T00:00:00.100+0000"
//                                + "\",\"toTime\":\"1970-01-01T00:00:00.200+0000\"}]\n")));
//
//        String json = "[{\"id\":3,\"room\":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},"
//                + "\"faculty\":\"b\",\"facultySpecific\":true,\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\""
//                + ":\"r.jursevskis@student.tudelft.nl\",\"password\":\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\",\"name\":\"Renats"
//                + "\",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\""
//                + "},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\",\"toTime\":\"2020-03-19T12:00:00.000+0000\"},{\"id\":4,\"room\""
//                + ":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},\"faculty\":\"b\",\"facultySpecific\":true,"
//                + "\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\":\"r.jursevskis@student.tudelft.nl\",\"password\":"
//                + "\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\",\"name\":\"Renats\",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,"
//                + "\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"1970-01-01T00:00:00.100+0000"
//                + "\",\"toTime\":\"1970-01-01T00:00:00.200+0000\"}]\n";
//
//        RoomReservation roomReservation = JsonMapper.roomReservationMapper(json);
//
//        assertEquals(roomReservation, JsonMapper.roomReservationMapper(ServerCommunication.getRoomReservations()));
//    }


}