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
 * Tests the DishRelated class by using a mock server to simulate client to server communication.
 */
class DishServerCommunicationTest {
    public WireMockServer wireMockServer;

    /**
     * Sets up the mock server before each test.
     */
    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        stubFor(post(urlEqualTo("/dish/add?name=test&menuId=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/dish/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/food_order/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/add?restaurantId=1&deliverLocation=1&deliverTimeMs=1"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/all")).willReturn(aResponse().withStatus(200).withBody("Message3")));
        stubFor(get(urlEqualTo("/food_order/past")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/future")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/menu/add?name=test&restaurantId=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/menu/delete/1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/addDish?id=1&name=test")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/food_order/cancel/1")).willReturn(aResponse().withStatus(200).withBody("201")));
        stubFor(post(urlEqualTo("/dish/update?id=1&attribute=a&value=a")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/dish/addAllergy?id=1&allergyName=test")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/dish/filter?query=name:Tosti")).willReturn(aResponse().withStatus(200).withBody("Message11")));
        stubFor(get(urlEqualTo("/allergy/filter?query=name:Lactose")).willReturn(aResponse().withStatus(200).withBody("Message12")));
        stubFor(get(urlEqualTo("/food_order/active")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/restaurant_hours/delete?id=1&day=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(delete(urlEqualTo("/restaurant_hours/delete?id=1&date=1")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/restaurant_hours/add?restaurantId=1&date=1&startTimeS=1&endTimeS=2"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/restaurant_hours/all")).willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(get(urlEqualTo("/restaurant_hours/find/1/1"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/restaurant_hours/update?id=1&attribute=attribute&value=value"))
                .willReturn(aResponse().withStatus(200).withBody("200")));
        stubFor(post(urlEqualTo("/food_order/addFeedback?id=1&feedback=true")).willReturn(aResponse().withStatus(200).withBody("201")));
        stubFor(post(urlEqualTo("/restaurant/addFeedback?id=1&feedback=true")).willReturn(aResponse().withStatus(200).withBody("201")));
    }

    /**
     * Tests adding dishes to the server.
     */
    @Test
    public void testSuccessfulAddDishes() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.addDish("test", 1));
    }

    /**
     * Tests update a dish in the database.
     */
    @Test
    public void testSuccessfulUpdatingDish() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.updateDish(1, "a", "a"));
    }

    /**
     * Tests adding an allergy to a dishes to the server.
     */
    @Test
    public void testSuccessfulAddAllergyToDish() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.addAllergyToDish("test", 1));
    }

    /**
     * Tests deleting dishes from the server.
     */
    @Test
    public void testSuccessfulDeleteDishes() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.deleteDish(1));
    }

    /**
     * Tests adding food orders to the server.
     */
    @Test
    public void testSuccessfulAddingFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.addFoodOrder(1, 1, 1));
    }

    /**
     * Tests update a food order in the database.
     */
    @Test
    public void testSuccessfulUpdatingFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.updateFoodOrder(1, "a", "a"));
    }

    /**
     * Tests adding a dish to a food order.
     */
    @Test
    public void testSuccessfulAddDishToFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.addDishToFoodOrder(1, "test"));
    }

    /**
     * Tests deleting food orders from the server.
     */
    @Test
    public void testSuccessfulDeletingFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.deleteFoodOrder(1));
    }

    /**
     * Tests getting all food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllFoodOrders() {
        assertEquals("Message3", DishServerCommunication.getAllFoodOrders());
    }

    /**
     * Tests getting all active food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllActiveFoodOrders() {
        assertEquals("200", DishServerCommunication.getAllActiveFoodOrders());
    }

    /**
     * Tests getting all future food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureFoodOrders() {
        assertEquals("200", DishServerCommunication.getAllFutureFoodOrders());
    }

    /**
     * Tests getting all previous food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllPastFoodOrders() {
        assertEquals("200", DishServerCommunication.getAllPreviousFoodOrders());
    }

    /**
     * Tests adding menu to the server.
     */
    @Test
    public void testSuccessfulAddMenu() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.addMenu("test", 1));
    }

    /**
     * Tests deleting a menu from the server.
     */
    @Test
    public void testSuccessfulDeleteMenu() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.deleteMenu(1));
    }

    /**
     * Tests the response when the filterDishes request is successful.
     */
    @Test
    public void testSuccessfulFilterDishes() {
        assertEquals("Message11", DishServerCommunication.filterDishes("name:Tosti"));
    }

    /**
     * Tests the response when the filterAllergies request is successful.
     */
    @Test
    public void testSuccessfulFilterAllergies() {
        assertEquals("Message12", DishServerCommunication.filterAllergies("name:Lactose"));
    }

    /**
     * Tests the response when the cancelFoodOrder request is successful.
     */
    @Test
    public void testCancelFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(201), DishServerCommunication.cancelFoodOrder(1));
    }

    /**
     * Tests deleting restaurant hours by day from the server.
     */
    @Test
    public void testSuccessfulDeleteRestaurantHoursByDay() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.deleteRestaurantHours(1, 1));
    }

    /**
     * Tests deleting restaurant hours by date from the server.
     */
    @Test
    public void testSuccessfulDeleteRestaurantHoursByDate() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.deleteRestaurantHours(1, 1L));
    }

    /**
     * Tests adding restaurant hours to the database.
     */
    @Test
    public void testSuccessfulAddRestaurantHours() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.addRestaurantHours(1, 1L, 1, 2));
    }

    /**
     * Tests getting all restaurant hours from the server.
     */
    @Test
    public void testGetRestaurantHours() {
        assertEquals("200", DishServerCommunication.getRestaurantHours());
    }

    /**
     * Tests retrieving specific restaurant opening hours for specific day in the database by id.
     */
    @Test
    public void testFindRestaurantHours() {
        assertEquals("200", DishServerCommunication.findRestaurantHours(1,1));
    }

    /**
     * Tests updating a given attribute of restaurant hours.
     */
    @Test
    public void testUpdateRestaurantHours() {
        assertEquals(ErrorMessages.getErrorMessage(200), DishServerCommunication.updateRestaurantHours(1,"attribute","value"));
    }

    /**
     * Tests the adding of feedback to a food order
     */
    @Test
    public void testAddFeedbackFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(201), DishServerCommunication.addFeedbackFoodOrder(1,true));
    }

    /**
     * Tests the adding of feedback to a food order
     */
    @Test
    public void testAddFoodFeedbackRestaurant() {
        assertEquals(ErrorMessages.getErrorMessage(201), DishServerCommunication.addFeedbackFoodOrder(1,true));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}