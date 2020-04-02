package nl.tudelft.oopp.demo.communication;

import com.github.tomakehurst.wiremock.WireMockServer;
import nl.tudelft.oopp.demo.errors.ErrorMessages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the DishRelated class by using a mock server to simulate client to server communication.
 */
class DishRelatedTest {
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
        stubFor(get(urlEqualTo("/dish/filter?query=name:Tosti")).willReturn(aResponse().withStatus(200).withBody("Message11")));
        stubFor(get(urlEqualTo("/allergy/filter?query=name:Lactose")).willReturn(aResponse().withStatus(200).withBody("Message12")));
    }

    /**
     * Tests adding dishes to the server.
     */
    @Test
    public void testSuccessfulAddDishes() {
        assertEquals("200", DishRelated.addDish("test", 1));
    }

    /**
     * Tests update a dish in the database.
     */
    @Test
    public void testSuccessfulUpdatingDish() {
        assertEquals("200", DishRelated.updateDish(1, "a", "a"));
    }

    /**
     * Tests adding an allergy to a dishes to the server.
     */
    @Test
    public void testSuccessfulAddAllergyToDish() {
        assertEquals("200", DishRelated.addAllergyToDish("test", 1));
    }

    /**
     * Tests deleting dishes from the server.
     */
    @Test
    public void testSuccessfulDeleteDishes() {
        assertEquals("200", DishRelated.deleteDish(1));
    }

    /**
     * Tests adding food orders to the server.
     */
    @Test
    public void testSuccessfulAddingFoodOrder() {
        assertEquals("200", DishRelated.addFoodOrder(1, 1, 1));
    }

    /**
     * Tests update a food order in the database.
     */
    @Test
    public void testSuccessfulUpdatingFoodOrder() {
        assertEquals("200", DishRelated.updateFoodOrder(1, "a", "a"));
    }

    /**
     * Tests adding a dish to a food order.
     */
    @Test
    public void testSuccessfulAddDishToFoodOrder() {
        assertEquals("200", DishRelated.addDishToFoodOrder(1, "test"));
    }

    /**
     * Tests deleting food orders from the server.
     */
    @Test
    public void testSuccessfulDeletingFoodOrder() {
        assertEquals("200", DishRelated.deleteFoodOrder(1));
    }

    /**
     * Tests getting all food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllFoodOrders() {
        assertEquals("200", DishRelated.getAllFoodOrders());
    }

    /**
     * Tests getting all future food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllFutureFoodOrders() {
        assertEquals("200", DishRelated.getAllFutureFoodOrders());
    }

    /**
     * Tests getting all previous food orders from the server.
     */
    @Test
    public void testSuccessfulGetAllPastFoodOrders() {
        assertEquals("200", DishRelated.getAllPreviousFoodOrders());
    }

    /**
     * Tests adding menu to the server.
     */
    @Test
    public void testSuccessfulAddMenu() {
        assertEquals("200", DishRelated.addMenu("test", 1));
    }

    /**
     * Tests deleting a menu from the server.
     */
    @Test
    public void testSuccessfulDeleteMenu() {
        assertEquals("200", DishRelated.deleteMenu(1));
    }

    /**
     * Tests the response when the filterDishes request is successful.
     */
    @Test
    public void testSuccessfulFilterDishes() {
        assertEquals("Message11", DishRelated.filterDishes("name:Tosti"));
    }

    /**
     * Tests the response when the filterAllergies request is successful.
     */
    @Test
    public void testSuccessfulFilterAllergies() {
        assertEquals("Message12", DishRelated.filterAllergies("name:Lactose"));
    }

    /**
     * Tests the response when the cancelFoodOrder request is successful.
     */
    @Test
    public void testCancelFoodOrder() {
        assertEquals(ErrorMessages.getErrorMessage(201), DishRelated.cancelFoodOrder(1));
    }

    /**
     * Stops the mock server after each test.
     */
    @AfterEach
    public void cleanup() {
        wireMockServer.stop();
    }
}