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

import nl.tudelft.oopp.demo.errors.ErrorMessages;

import org.eclipse.jetty.server.Server;
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
        stubFor(get(urlEqualTo("/room/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(delete(urlEqualTo("/building/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/building/find/1")).willReturn(aResponse().withStatus(200).withBody("Message4")));
        stubFor(post(urlEqualTo("/building/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room/find/1")).willReturn(aResponse().withStatus(200).withBody("Message5")));
        stubFor(post(urlEqualTo("/user/changePassword?password=Password")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/room/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room/add?name=a&studySpecific=a&screen=true&projector=true&buildingId=1&capacity=1&plugs=1&status=A"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/building/add?name=a&street=a&houseNumber=1&faculty=faculty"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/room_reservation/add?roomId=1&fromTimeMs=1&toTimeMs=2"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/all")).willReturn(aResponse().withStatus(200).withBody("RoomReservations")));
        stubFor(post(urlEqualTo("/room_reservation/update?id=1&attribute=a&value=b")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/room_reservation/delete?id=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/room/2")).willReturn(aResponse().withStatus(200).withBody("Message6")));
        stubFor(get(urlEqualTo("/user/info")).willReturn(aResponse().withStatus(200).withBody("Information")));
        stubFor(post(urlEqualTo("/login")).willReturn(aResponse().withStatus(200).withBody("token")));
        stubFor(post(urlEqualTo("/dish/add?name=test&menuId=1&price=3&description=A&image=B")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/dish/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/food_order/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/add?restaurantId=1&deliverLocation=1&deliverTimeMs=1"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/all")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/past")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/future")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/menu/add?name=test&restaurantId=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/menu/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/addDish?id=1&name=test")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/cancel/1")).willReturn(aResponse().withStatus(200).withBody("201")));
        stubFor(post(urlEqualTo("/dish/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/dish/addAllergy?id=1&allergyName=test")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/find/10")).willReturn(aResponse().withStatus(200).withBody("Message10")));
        stubFor(get(urlEqualTo("/room_reservation/cancel/1")).willReturn(aResponse().withStatus(200).withBody("201")));
        stubFor(get(urlEqualTo("/room/filter?query=name:Auditorium")).willReturn(aResponse().withStatus(200).withBody("Message15")));
        stubFor(get(urlEqualTo("/dish/filter?query=name:Tosti")).willReturn(aResponse().withStatus(200).withBody("Message11")));
        stubFor(get(urlEqualTo("/allergy/filter?query=name:Lactose")).willReturn(aResponse().withStatus(200).withBody("Message12")));
        stubFor(get(urlEqualTo("/room_reservation/past")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/room_reservation/future")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/all")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/future")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/past")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/bike_reservation/cancel/1")).willReturn(aResponse().withStatus(200).withBody("201")));
    }

    /**
     * Test for getting all reservations for a specific room from server
     */
    @Test
    public void testSuccessfulGetAllReservationsForRoom() {
        assertEquals("Message6", ServerCommunication.getRoomReservationsForRoom(2));
    }

    /**
     * Tests getting all future room reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureRoomReservations() {
        assertEquals("200", ServerCommunication.getAllFutureRoomReservations());
    }

    /**
     * Tests getting all previous room reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllPastRoomReservations() {
        assertEquals("200", ServerCommunication.getAllPreviousRoomReservations());
    }

    /**
     * Tests getting all bike reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllBikeReservations() {
        assertEquals("200", ServerCommunication.getAllBikeReservations());
    }

    /**
     * Tests getting all future bike reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureBikeReservations() {
        assertEquals("200", ServerCommunication.getAllFutureBikeReservations());
    }

    /**
     * Tests getting all previous bike reservations from the server.
     */
    @Test
    public void testSuccessfulGetAllPastBikeReservations() {
        assertEquals("200", ServerCommunication.getAllPreviousBikeReservations());
    }


    /**
     * Tests adding dishes to the server.
     */
    @Test
    public void testSuccessfulAddDishes() {
        assertEquals("200", ServerCommunication.addDish("test", 1, 3, "A", "B"));
    }

    /**
     * Tests update a dish in the database.
     */
    @Test
    public void testSuccessfulUpdatingDish() {
        assertEquals("200", ServerCommunication.updateDish(1, "a", "a"));
    }

    /**
     * Tests adding an allergy to a dishes to the server.
     */
    @Test
    public void testSuccessfulAddAllergyToDish() {
        assertEquals("200", ServerCommunication.addAllergyToDish("test", 1));
    }

    /**
     * Tests deleting dishes from the server.
     */
    @Test
    public void testSuccessfulDeleteDishes() {
        assertEquals("200", ServerCommunication.deleteDish(1));
    }

    /**
     * Tests adding food orders to the server.
     */
    @Test
    public void testSuccessfulAddingFoodOrder() {
        assertEquals("200", ServerCommunication.addFoodOrder(1, 1, 1));
    }

    /**
     * Tests update a food order in the database.
     */
    @Test
    public void testSuccessfulUpdatingFoodOrder() {
        assertEquals("200", ServerCommunication.updateFoodOrder(1, "a", "a"));
    }

    /**
     * Tests adding a dish to a food order.
     */
    @Test
    public void testSuccessfulAddDishToFoodOrder() {
        assertEquals("200", ServerCommunication.addDishToFoodOrder(1, "test"));
    }

    /**
     * Tests deleting food orders from the server.
     */
    @Test
    public void testSuccessfulDeletingFoodOrder() {
        assertEquals("200", ServerCommunication.deleteFoodOrder(1));
    }

    /**
     * Tests getting all food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllFoodOrders() {
        assertEquals("200", ServerCommunication.getAllFoodOrders());
    }

    /**
     * Tests getting all future food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureFoodOrders() {
        assertEquals("200", ServerCommunication.getAllFutureFoodOrders());
    }

    /**
     * Tests getting all previous food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllPastFoodOrders() {
        assertEquals("200", ServerCommunication.getAllPreviousFoodOrders());
    }

    /**
     * Tests adding menu to the server.
     */
    @Test
    public void testSuccessfulAddMenu() {
        assertEquals("Successfully executed.", ServerCommunication.addMenu("test", 1));
    }

    /**
     * Tests deleting a menu from the server.
     */
    @Test
    public void testSuccessfulDeleteMenu() {
        assertEquals("200", ServerCommunication.deleteMenu(1));
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
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addRoom("a", 1, "a", true, true, 1, 1, "A"));
    }

    /**
     * Tests the response when the filterRooms request is successful.
     */
    @Test
    public void testSuccessfulFilterRooms() {
        assertEquals("Message15", ServerCommunication.filterRooms("name:Auditorium"));
    }

    /**
     * Tests the response when the filterDishes request is successful.
     */
    @Test
    public void testSuccessfulFilterDishes() {
        assertEquals("Message11", ServerCommunication.filterDishes("name:Tosti"));
    }

    /**
     * Tests the response when the filterAllergies request is successful.
     */
    @Test
    public void testSuccessfulFilterAllergies() {
        assertEquals("Message12", ServerCommunication.filterAllergies("name:Lactose"));
    }

    /**
     * Tests the response when the addBuilding request is successful.
     */
    @Test
    public void testSuccessfulAddBuilding() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addBuilding("a", "a", 1, "faculty"));
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
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.addRoomReservation(1, 1, 2));
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
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.updateRoomReservation(1, "a", "b"));
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
     * Tests the response when the loginUser request is successful.
     */
    @Test
    public void testSuccessfulChangePassword() {
        assertEquals(ErrorMessages.getErrorMessage(200), ServerCommunication.changeUserPassword("Password"));
    }

    /**
     * Tests the response when the cancelRoomReservation request is successful.
     */
    @Test
    public void testCancelRoomReservation() {
        assertEquals(ErrorMessages.getErrorMessage(201), ServerCommunication.cancelRoomReservation(1));
    }

    /**
     * Tests the response when the cancelBikeReservation request is successful.
     */
    @Test
    public void testCancelBikeReservation() {
        assertEquals(ErrorMessages.getErrorMessage(201), ServerCommunication.cancelBikeReservation(1));
    }

    /**
     * Tests the response when the cancelFoodOrder request is successful.
     */
    @Test
    public void testCancelFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(201), ServerCommunication.cancelFoodOrder(1));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}