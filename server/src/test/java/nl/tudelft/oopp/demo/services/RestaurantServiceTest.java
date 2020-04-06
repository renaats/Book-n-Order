package nl.tudelft.oopp.demo.services;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_CREDENTIALS;
import static nl.tudelft.oopp.demo.security.SecurityConstants.EXPIRATION_TIME;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.auth0.jwt.JWT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;

/**
 * Tests the Restaurant service.
 */
@DataJpaTest
public class RestaurantServiceTest {
    @TestConfiguration
    static class RestaurantServiceTestConfiguration {
        @Bean
        public RestaurantService restaurantService() {
            return new RestaurantService();
        }
    }

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    UserRepository userRepository;

    Building building;
    Building building2;
    Restaurant restaurant;
    Restaurant restaurant2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(building);

        building2 = new Building("EWI2", "Mekelweg2", "EWI", 42);
        buildingRepository.save(building2);

        restaurant = new Restaurant(building, "Hangout", "restaurant@tudelft.nl");

        restaurant2 = new Restaurant(building2, "Food station", "restaurant2@tudelft.nl");
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(restaurantService);
    }

    /**
     * Tests the saving and retrieval of an instance of Restaurant.
     */
    @Test
    public void testCreate() {
        assertEquals(ADDED, restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl"));
        assertEquals(Collections.singletonList(restaurant), restaurantService.all());
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testCreateIllegalBuilding() {
        assertEquals(BUILDING_NOT_FOUND, restaurantService.add(-3,"The Ghost Restaurant", "restaurant@tudelft.nl"));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(restaurantService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    public void testFindExisting() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        assertNotNull(restaurantService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExistingInstance() {
        assertEquals(RESTAURANT_NOT_FOUND, restaurantService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testUpdateNonExistingAttribute() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, restaurantService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the name by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeName() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        assertNotEquals("Food Station", restaurantService.find(id).getName());
        restaurantService.update(id, "name", "Food Station");
        assertEquals("Food Station", restaurantService.find(id).getName());
    }

    /**
     * Tests the change of the building by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeBuilding() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        assertNotEquals(building2, restaurantService.find(id).getBuilding());
        restaurantService.update(id, "building", building2.getId().toString());
        assertEquals(building2, restaurantService.find(id).getBuilding());
    }

    /**
     * Tests the change of the email by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeEmail() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        restaurantService.update(id, "email", "newEmail");
        assertEquals("newEmail", restaurantService.find(id).getEmail());
        assertEquals(WRONG_CREDENTIALS, restaurantService.update(id, "email", "newEmail"));
    }

    /**
     * Tests the change of the email by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl")
    public void testGetFeedback() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        restaurantService.addFeedback(id, true);
        assertEquals(1, restaurantService.find(id).getFeedbackCounter());
    }

    /**
     * Tests the change of the email by admins by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "ADMIN"})
    public void testChangeEmailAdmin() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        restaurantService.update(id, "email", "newEmail");
        restaurantService.update(id, "email", "newEmail2");
        assertEquals("newEmail2", restaurantService.find(id).getEmail());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    public void testMultipleInstances() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        restaurantService.add(restaurant2.getBuilding().getId(), restaurant2.getName(), "restaurant2@tudelft.nl");
        assertEquals(2, restaurantService.all().size());
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        restaurants.add(restaurant2);
        assertEquals(restaurants, restaurantService.all());
    }

    /**
     * Tests the retrieval of own restaurants.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testRetrieveOwn() {
        AppUser appUser = new AppUser();
        appUser.setEmail("restaurant@tudelft.nl");
        userRepository.save(appUser);
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject("restaurant@tudelft.nl")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        restaurantService.add(restaurant2.getBuilding().getId(), restaurant2.getName(), "restaurant2@tudelft.nl");
        assertEquals(Collections.singletonList(restaurant), restaurantService.owned(request));
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testDelete() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        int id = restaurantService.all().get(0).getId();
        assertEquals(EXECUTED, restaurantService.delete(id));
        assertEquals(0, restaurantService.all().size());
    }

    /**
     * Tests the adding of feedback.
     */
    @Test
    public void testAddFeedback() {
        restaurantService.add(restaurant.getBuilding().getId(), restaurant.getName(), "restaurant@tudelft.nl");
        restaurantService.addFeedback(restaurantService.all().get(0).getId(), true);
        assertEquals(1,restaurantService.all().get(0).getFeedbackCounter());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    public void testDeleteIllegal() {
        assertEquals(RESTAURANT_NOT_FOUND, restaurantService.delete(0));
    }
}