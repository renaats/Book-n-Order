package nl.tudelft.oopp.demo.communication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.FoodOrder;
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
    void testBuildingMapper() throws JsonProcessingException {
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200)
                .withBody("{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1}")));
        assertEquals(JsonMapper.buildingMapper("{\"id\":1,\"name\":\"testffes\",\"street\":\"1\",\"houseNumber\":1}"),
                JsonMapper.buildingMapper(BuildingServerCommunication.findBuilding(1)));
    }

    @Test
    void testBuildingListMapper() throws IOException {
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
                JsonMapper.buildingListMapper(BuildingServerCommunication.getBuildings()));
    }

    @Test
    void testRoomMapper() {
        stubFor(get(urlEqualTo("/room/find/4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":113,\"name\":\"test\",\"building\":{\"id\":85"
                                + ",\"name\":\"de3ded\",\"street\""
                                + ":\"est\",\"faculty\":\"\",\"houseNumber\""
                                + ":0},\"studySpecific\":\"Computer Science and Engineering\""
                                + ",\"projector\":false,\"screen\":true,\"capacity\":555555,\"plugs\""
                                + ":555,\"status\":\"Closed\"}")));
        assertEquals(
                JsonMapper
                        .roomMapper(
                                "{\"id\":113,\"name\":\"test\",\"building\":{\"id\":85"
                                        + ",\"name\":\"de3ded\",\"street\""
                                        + ":\"est\",\"faculty\":\"\",\"houseNumber\""
                                        + ":0},\"studySpecific\":\"Computer Science and Engineering\""
                                        + ",\"projector\":false,\"screen\":true,\"capacity\":555555,\"plugs\""
                                        + ":555,\"status\":\"Closed\"}"),
                JsonMapper.roomMapper((RoomServerCommunication.findRoom(4))));
    }

    @Test
    void testRoomListMapper() throws IOException {
        List<Room> room =
                new ArrayList<>(Objects
                        .requireNonNull(JsonMapper
                                .roomListMapper("[{\"id\":113,\"name\":\"test\",\"building\":"
                                        + "{\"id\":85,\"name\":\"de3ded\",\"street\":\"est\",\"faculty\":\""
                                        + "\",\"houseNumber\":0},\"studySpecific\":\"Computer Science and Engineering"
                                        + "\",\"projector\":false,\"screen\":true,\"capacity\":555555"
                                        + ",\"plugs\":555,\"status\":\"Closed\"},{\"id\":114,\"name\":"
                                        + "\"test\",\"building\":{\"id\":85,\"name\":\"de3ded\",\"street"
                                        + "\":\"est\",\"faculty\":\"\",\"houseNumber\":0},\"studySpecific"
                                        + "\":\"Computer Science and Engineering\",\"projector\":false,\"screen"
                                        + "\":true,\"capacity\":5,\"plugs\":5,\"status\":\"Maintenance\"}]\n")));
        stubFor(get(urlEqualTo("/room/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":113,\"name\":\"test\",\"building\":"
                                + "{\"id\":85,\"name\":\"de3ded\",\"street\":\"est\",\"faculty\":\""
                                + "\",\"houseNumber\":0},\"studySpecific\":\"Computer Science and Engineering"
                                + "\",\"projector\":false,\"screen\":true,\"capacity\":555555"
                                + ",\"plugs\":555,\"status\":\"Closed\"},{\"id\":114,\"name\":"
                                + "\"test\",\"building\":{\"id\":85,\"name\":\"de3ded\",\"street"
                                + "\":\"est\",\"faculty\":\"\",\"houseNumber\":0},\"studySpecific"
                                + "\":\"Computer Science and Engineering\",\"projector\":false,\"screen"
                                + "\":true,\"capacity\":5,\"plugs\":5,\"status\":\"Maintenance\"}]\n")));
        assertEquals(room, JsonMapper.roomListMapper(RoomServerCommunication.getRooms()));
    }

    @Test
    void testBuildingHoursMapper() throws JsonProcessingException {
        stubFor(get(urlEqualTo("/building_hours/find/1/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":3,\"day\":1,\"building\":{\"id\":1,\"name\":\"test\","
                                + "\"street\":\"1\",\"houseNumber\":1},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}")));
        String json = "{\"id\":3,\"day\":1,\"building\":{\"id\":1,\"name\":\"test\""
                + ",\"street\":\"1\",\"houseNumber\":1},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}";

        BuildingHours buildingHours = JsonMapper.buildingHoursMapper(json);

        assertEquals(buildingHours, JsonMapper.buildingHoursMapper(BuildingServerCommunication.findBuildingHours(1, 1)));
    }

    @Test
    void testRestaurantHoursMapper() {
        stubFor(get(urlEqualTo("/restaurant_hours/find/1/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":5,\"day\":1,\"restaurant\":{\"id\":4,\"building\":"
                                + "{\"id\":1,\"name\":\"test\",\"street\":\"1\",\"houseNumber\":1},"
                                + "\"name\":\"TestRestaurant\",\"menu\":null},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}")));

        String json = "{\"id\":5,\"day\":1,\"restaurant\":{\"id\":4,\"building\":{\"id\":1,\"name\":"
                + "\"test\",\"street\":\"1\",\"houseNumber\":1},\"name\":\"TestRestaurant\","
                + "\"menu\":null},\"startTime\":\"00:16:40\",\"endTime\":\"00:33:20\"}";

        try {
            RestaurantHours restaurantHours = JsonMapper.restaurantHoursMapper(json);
            assertEquals(restaurantHours, JsonMapper.restaurantHoursMapper(RestaurantServerCommunication.findRestaurantHours(1, 1)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testRoomReservationMapper() {
        stubFor(get(urlEqualTo("/room_reservation/find/3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":3,\"room\":{\"id\":113,\"name\":\"test\",\"building\":{\"id\":85,\"name\":\"de3ded\","
                                + "\"street\":\"est\",\"faculty\":\"\",\"houseNumber\":0},\"studySpecific\":"
                                + "\"Computer Science and Engineering\",\"projector\":false,\""
                                + "screen\":true,\"capacity\":555555,\"plugs\":555,\"status\":\"Closed\"},\"appUser"
                                + "\":{\"email\":\""
                                + "r.jursevskis@student.tudelft.nl\",\"password\":\"abc\",\"name\":\"Renats\""
                                + ",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,"
                                + "\"confirmationNumber\":183937,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\""
                                + "id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\","
                                + "\"toTime\":\"2020-03-19T12:00:00.000+0000\"}")));

        String json = "{\"id\":3,\"room\":{\"id\":113,\"name\":\"test\",\"building\":{\"id\":85,\"name\":\"de3ded\","
                + "\"street\":\"est\",\"faculty\":\"\",\"houseNumber\":0},\"studySpecific\":"
                + "\"Computer Science and Engineering\",\"projector\":false,\""
                + "screen\":true,\"capacity\":555555,\"plugs\":555,\"status\":\"Closed\"},\"appUser"
                + "\":{\"email\":\""
                + "r.jursevskis@student.tudelft.nl\",\"password\":\"abc\",\"name\":\"Renats\""
                + ",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,"
                + "\"confirmationNumber\":183937,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\""
                + "id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\","
                + "\"toTime\":\"2020-03-19T12:00:00.000+0000\"}";

        RoomReservation roomReservation = JsonMapper.roomReservationMapper(json);
        assertEquals(roomReservation, JsonMapper.roomReservationMapper(RoomServerCommunication.findRoomReservation(3)));
    }

    @Test
    void testRoomReservationListMapper() {
        stubFor(get(urlEqualTo("/room_reservation/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":3,\"room\":{\"id\":2,\"name\":\"a\",\"building\":{"
                                + "\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},"
                                + "\"projector\":true,\"screen"
                                + "\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\""
                                + ":\"r.jursevskis@student.tudelft.nl\",\"password\":"
                                + "\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\","
                                + "\"name\":\"Renats"
                                + "\",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\","
                                + "\"loggedIn\":true,\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\""
                                + "},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\","
                                + "\"toTime\":\"2020-03-19T12:00:00.000+0000\"},{\"id\":4,\"room\""
                                + ":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\","
                                + "\"houseNumber\":24},"
                                + "\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\":"
                                + "\"r.jursevskis@student.tudelft.nl\",\"password\":"
                                + "\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\",\"name\":\"Renats\","
                                + "\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,"
                                + "\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,"
                                + "\"name\":\"ROLE_USER\"}]},\"fromTime\":\"1970-01-01T00:00:00.100+0000"
                                + "\",\"toTime\":\"1970-01-01T00:00:00.200+0000\"}]")));

        String json = "[{\"id\":3,\"room\":{\"id\":2,\"name\":\"a\",\"building\":{"
                + "\"id\":1,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":24},"
                + "\"projector\":true,\"screen"
                + "\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\""
                + ":\"r.jursevskis@student.tudelft.nl\",\"password\":"
                + "\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\","
                + "\"name\":\"Renats"
                + "\",\"surname\":\"Jursevskis\",\"faculty\":\"EWI\","
                + "\"loggedIn\":true,\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\""
                + "},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromTime\":\"2020-03-19T11:30:00.000+0000\","
                + "\"toTime\":\"2020-03-19T12:00:00.000+0000\"},{\"id\":4,\"room\""
                + ":{\"id\":2,\"name\":\"a\",\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\","
                + "\"houseNumber\":24},"
                + "\"projector\":true,\"screen\":true,\"capacity\":10,\"plugs\":10},\"appUser\":{\"email\":"
                + "\"r.jursevskis@student.tudelft.nl\",\"password\":"
                + "\"$2a$10$gJ1P7tWDAlgq4VBlCBjK.uKgZPw0tKxG/NMSiGDfNtxKcEDJEIRVC\",\"name\":\"Renats\","
                + "\"surname\":\"Jursevskis\",\"faculty\":\"EWI\",\"loggedIn\":true,"
                + "\"confirmationNumber\":934789,\"roles\":[{\"id\":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,"
                + "\"name\":\"ROLE_USER\"}]},\"fromTime\":\"1970-01-01T00:00:00.100+0000"
                + "\",\"toTime\":\"1970-01-01T00:00:00.200+0000\"}]";

        try {
            List<RoomReservation> roomReservations = JsonMapper.roomReservationsListMapper(json);
            assertEquals(roomReservations, JsonMapper.roomReservationsListMapper(RoomServerCommunication.getRoomReservations()));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testBikeReservationMapper() {
        stubFor(get(urlEqualTo("/bike_reservation/find/5"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":5,\"bike\":{\"id\":2,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\""
                                + ",\"houseNumber\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\","
                                + "\"password\":\"$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\""
                                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\""
                                + "confirmationNumber\":466328,\"roles\":[{\""
                                + "id\":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromBuilding\":{\"id\":1,\"name\":\""
                                + "delia\",\"street\":\"bulevardi\",\"houseNumber\":50},\"toBuilding\":{\"id\":4,\"name\":\"alto\",\"street\""
                                + ":\"rruga\",\"houseNumber\":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\""
                                + ",\"toTime\":\"2020-03-19T19:00:00.000+0000\"}")));

        String json = "{\"id\":5,\"bike\":{\"id\":2,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber\":50},\""
                + "available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\"password\":\""
                + "$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\","
                + "\"name\":\"Alto\",\"surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\""
                + ":[{\"id\":2,\"name\":\"ROLE_ADMIN"
                + "\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromBuilding\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber\""
                + ":50},\"toBuilding\":{\"id\":4,"
                + "\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":50},\"fromTime\":"
                + "\"2020-03-19T14:00:00.000+0000\",\"toTime\":\"2020-03-19T19:00:00.000+0000\"}";

        BikeReservation bikeReservation = JsonMapper.bikeReservationMapper(json);
        assertEquals(bikeReservation, JsonMapper.bikeReservationMapper("{\"id\":5,\"bike\":{\"id\":2,\"location\""
                + ":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber\":50},\"available\":true},\"appUser\":{\"email\""
                + ":\"a.delia@student.tudelft.nl\",\"password\":\"$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\""
                + ":\"Alto\",\"surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\""
                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"fromBuilding\":{\"id\":1,\"name\":\"delia\",\"street\":"
                + "\"bulevardi\",\"houseNumber\":50},\"toBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":50},\""
                + "fromTime\":\"2020-03-19T14:00:00.000+0000\",\"toTime\":\"2020-03-19T19:00:00.000+0000\"}"));
    }

    @Test
    void testBikeReservationListMapper() {
        stubFor(get(urlEqualTo("/bike_reservation/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":5,\"bike\":{\"id\":2,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\""
                                + ",\"houseNumber\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\""
                                + "password\":\"$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\"surname"
                                + "\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\":1,\""
                                + "name\":\"ROLE_USER\"},{\"id\":2,\"name\":\"ROLE_ADMIN\"}]},\"fromBuilding\":{\"id\":1,\"name\":\"delia\","
                                + "\"street\":\"bulevardi\",\"houseNumber\":50},\"toBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga"
                                + "\",\"houseNumber\":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\",\"toTime\":\"2020-03-19T19:00:00.000+0000"
                                + "\"},{\"id\":7,\"bike\":{\"id\":3,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber"
                                + "\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\"password\":\""
                                + "$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\"surname\":\"Delia\",\"faculty"
                                + "\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\":1,\"name\":\"ROLE_USER\"},{\"id\":2,"
                                + "\"name\":\"ROLE_ADMIN\"}]},\"fromBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":50},"
                                + "\"toBuilding\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber\""
                                + ":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\""
                                + ",\"toTime\":\"2020-03-19T19:00:00.000+0000\"}]")));

        String json = "[{\"id\":5,\"bike\":{\"id\":2,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\""
                + ",\"houseNumber\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\""
                + "password\":\"$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\"surname"
                + "\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\":1,\""
                + "name\":\"ROLE_USER\"},{\"id\":2,\"name\":\"ROLE_ADMIN\"}]},\"fromBuilding\":{\"id\":1,\"name\":\"delia\","
                + "\"street\":\"bulevardi\",\"houseNumber\":50},\"toBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga"
                + "\",\"houseNumber\":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\",\"toTime\":\"2020-03-19T19:00:00.000+0000"
                + "\"},{\"id\":7,\"bike\":{\"id\":3,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber"
                + "\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\"password\":\""
                + "$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\"surname\":\"Delia\",\"faculty"
                + "\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\":1,\"name\":\"ROLE_USER\"},{\"id\":2,"
                + "\"name\":\"ROLE_ADMIN\"}]},\"fromBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":50},"
                + "\"toBuilding\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\","
                + "\"houseNumber\":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\""
                + ",\"toTime\":\"2020-03-19T19:00:00.000+0000\"}]";

        List<BikeReservation> bikeReservations = null;
        try {
            bikeReservations = JsonMapper.bikeReservationsListMapper(json);
            assertEquals(bikeReservations, JsonMapper.bikeReservationsListMapper("[{\"id\":5,\"bike\":{"
                    + "\"id\":2,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\""
                    + ",\"houseNumber\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\""
                    + "password\":\"$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\"surname"
                    + "\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\":1,\""
                    + "name\":\"ROLE_USER\"},{\"id\":2,\"name\":\"ROLE_ADMIN\"}]},\"fromBuilding\":{\"id\":1,\"name\":\"delia\","
                    + "\"street\":\"bulevardi\",\"houseNumber\":50},\"toBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga"
                    + "\",\"houseNumber\":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\",\"toTime\":\"2020-03-19T19:00:00.000+0000"
                    + "\"},{\"id\":7,\"bike\":{\"id\":3,\"location\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber"
                    + "\":50},\"available\":true},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl\",\"password\":\""
                    + "$2a$10$egMmgdg/NvIsUxyl1bj4luOF/4xU1/6xAkD0b20WRrzocoTrCDNjy\",\"name\":\"Alto\",\"surname\":\"Delia\",\"faculty"
                    + "\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":466328,\"roles\":[{\"id\":1,\"name\":\"ROLE_USER\"},{\"id\":2,"
                    + "\"name\":\"ROLE_ADMIN\"}]},\"fromBuilding\":{\"id\":4,\"name\":\"alto\",\"street\":\"rruga\",\"houseNumber\":50},"
                    + "\"toBuilding\":{\"id\":1,\"name\":\"delia\",\"street\":\"bulevardi\",\"houseNumber\""
                    + ":50},\"fromTime\":\"2020-03-19T14:00:00.000+0000\""
                    + ",\"toTime\":\"2020-03-19T19:00:00.000+0000\"}]"));
        } catch (JsonProcessingException e) {
            fail();
        }

    }

    @Test
    void testFoodOrderMapper() {
        stubFor(get(urlEqualTo("/food_order/find/4"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":4,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                                + ":null,\"dishOrders\":[]}")));

        String json = "{\"id\":4,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                + ":null,\"dishOrders\":[]}";

        FoodOrder foodOrder = JsonMapper.foodOrderMapper(json);
        assertEquals(foodOrder, JsonMapper.foodOrderMapper("{\"id\":4,\"restaurant\":{\"id\":2,\"building\""
                + ":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                + ":null,\"dishOrders\":[]}"));
    }

    @Test
    void testFoodOrderListMapper() {
        stubFor(get(urlEqualTo("/food_order/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"id\":4,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                                + ":null,\"dishOrders\":[]},{\"id\":5,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\""
                                + ":\"rruga\""
                                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                                + ":null,\"dishOrders\":[]}]")));

        String json = "[{\"id\":4,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                + ":null,\"dishOrders\":[]},{\"id\":5,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                + ":null,\"dishOrders\":[]}]";

        List<FoodOrder> foodOrders = null;
        try {
            foodOrders = JsonMapper.foodOrdersListMapper(json);
            assertEquals(foodOrders, JsonMapper.foodOrdersListMapper("[{\"id\":4,\"restaurant\":{\"id\":2,\"building\""
                    + ":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                    + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                    + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                    + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                    + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                    + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                    + ":null,\"dishOrders\":[]},{\"id\":5,\"restaurant\":{\"id\":2,\"building\":{\"id\":1,\"name\":\"alto\",\"street\":\"rruga\""
                    + ",\"houseNumber\":24},\"name\":\"Ad\",\"menu\":null},\"appUser\":{\"email\":\"a.delia@student.tudelft.nl"
                    + "\",\"password\":\"$2a$10$x9OlEoG2tU62ASvF5mDrYurry8qijsQ/oZgRIGlvHeTxpC6.NgAsW\",\"name\":\"Alto\",\""
                    + "surname\":\"Delia\",\"faculty\":\"EWI\",\"loggedIn\":true,\"confirmationNumber\":33712,\"roles\":[{\"id\""
                    + ":2,\"name\":\"ROLE_ADMIN\"},{\"id\":1,\"name\":\"ROLE_USER\"}]},\"deliveryLocation\":{\"id\":1,\"name\":\""
                    + "alto\",\"street\":\"rruga\",\"houseNumber\":24},\"deliveryTime\":\"1970-01-01T00:00:00.100+0000\",\"menu\""
                    + ":null,\"dishOrders\":[]}]"));
        } catch (JsonProcessingException e) {
            fail();
        }
    }
}