package nl.tudelft.oopp.demo.services;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.ORDER_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESERVATION_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.RESTAURANT_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.WRONG_USER;
import static nl.tudelft.oopp.demo.security.SecurityConstants.EXPIRATION_TIME;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auth0.jwt.JWT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.MenuRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
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
 * Tests the FoodOrder service.
 */
@DataJpaTest
public class FoodOrderServiceTest {
    @TestConfiguration
    static class FoodOrderServiceTestConfiguration {
        @Bean
        public FoodOrderService foodOrderService() {
            return new FoodOrderService();
        }
    }

    @Autowired
    FoodOrderService foodOrderService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DishRepository dishRepository;

    @Autowired
    MenuRepository menuRepository;

    Building building;
    Building deliverLocation;
    Restaurant restaurant;
    AppUser appUser;
    AppUser appUser2;
    Date deliverTime;
    Date deliverTime2;
    long deliverTimeMilliseconds;
    long deliverTimeMilliseconds2;
    Menu menu;
    Dish dish1;
    Dish dish2;
    Set<Dish> dishes;
    FoodOrder foodOrder;
    FoodOrder foodOrder2;
    Set<Integer> dishIds;

    MockHttpServletRequest request;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("Sporthal", "Mekelweg", "EWI", 8);
        buildingRepository.save(building);

        restaurant = new Restaurant(building, "Cafe X", "restaurant@tudelft.nl");
        restaurantRepository.save(restaurant);

        appUser = new AppUser("l.j.jongejans@student.tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI", "CSE");
        appUser.setFoodOrder(new HashSet<>());
        userRepository.save(appUser);

        appUser2 = new AppUser("l.j.jongejans@tudelft.nl", "1234", "Liselotte", "Jongejans", "EWI", "CSE");
        appUser2.setFoodOrder(new HashSet<>());
        userRepository.save(appUser2);

        deliverLocation = new Building("EWI", "Mekelweg", "EWI", 4);
        buildingRepository.save(deliverLocation);

        deliverTime = new Date(11000000000L);
        deliverTimeMilliseconds = deliverTime.getTime();

        deliverTime2 = new Date(100000000000000L);
        deliverTimeMilliseconds2 = deliverTime2.getTime();

        menu = new Menu("Lunch Menu", restaurant);
        menuRepository.saveAndFlush(menu);

        dish1 = new Dish("Pizza", menu, 300, "Cooked", "123");
        dishRepository.saveAndFlush(dish1);
        dish2 = new Dish("Salad", menu, 400, "Grilled", "234");
        dishRepository.saveAndFlush(dish2);
        dishes = new HashSet<>();
        dishes.add(dish1);
        dishes.add(dish2);

        foodOrder = new FoodOrder(restaurant, appUser, deliverLocation, deliverTime, menuRepository.findAll().get(0));
        foodOrder2 = new FoodOrder(restaurant, appUser, deliverLocation, deliverTime2, menuRepository.findAll().get(0));

        request = new MockHttpServletRequest();
        String token = JWT.create()
                .withSubject(appUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        request.addHeader(HEADER_STRING, token);
    }

    /**
     * Tests the constructor creating a new instance of the service.
     */
    @Test
    public void testConstructor() {
        assertNotNull(foodOrderService);
    }

    /**
     * Tests the creation of an instance with an invalid restaurant id.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreateIllegalRestaurant() {
        assertEquals(RESTAURANT_NOT_FOUND, foodOrderService.add(request, 0, deliverLocation.getId(), deliverTimeMilliseconds));
    }

    /**
     * Tests the creation of an instance with an invalid user id.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreateIllegalUser() {
        assertEquals(NOT_FOUND,
                foodOrderService.add(new MockHttpServletRequest(), restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds));
    }

    /**
     * Tests the creation of an instance with an invalid deliver location id.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testCreateIllegalLocation() {
        assertEquals(BUILDING_NOT_FOUND, foodOrderService.add(request, restaurant.getId(),-1, deliverTimeMilliseconds));
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        assertNull(foodOrderService.find(0));
    }

    /**
     * Tests the search for an existing object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testFindExisting() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertNotNull(foodOrderService.find(id));
    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testUpdateNonExistingInstance() {
        assertEquals(RESERVATION_NOT_FOUND, foodOrderService.update(0, "a", "a"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testUpdateNonExistingAttribute() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, foodOrderService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the delivery location by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeDeliveryLocation() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertNotEquals(building, foodOrderService.find(id).getDeliveryLocation());
        foodOrderService.update(id, "deliverylocation", building.getId().toString());
        assertEquals(building, foodOrderService.find(id).getDeliveryLocation());
    }

    /**
     * Tests the change of the delivery time by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeDeliveryTime() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertNotEquals(deliverTimeMilliseconds2, foodOrderService.find(id).getDeliveryTime().getTime());
        foodOrderService.update(id, "deliverytime", ((Long) deliverTimeMilliseconds2).toString());
        assertEquals(deliverTimeMilliseconds2, foodOrderService.find(id).getDeliveryTime().getTime());
    }

    /**
     * Tests the change of the user by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeUser() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertNotEquals(appUser2, foodOrderService.find(id).getAppUser());
        foodOrderService.update(id, "useremail", appUser2.getEmail());
        assertEquals(appUser2, foodOrderService.find(id).getAppUser());
    }

    /**
     * Tests the change of active by using the service.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testChangeActive() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertTrue(foodOrderService.find(id).isActive());
        foodOrderService.update(id, "active", "false");
        assertFalse(foodOrderService.find(id).isActive());
    }

    /**
     * Tests the retrieval of multiple instances.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testMultipleInstances() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        assertEquals(2, foodOrderService.all().size());
        List<FoodOrder> foodOrders = new ArrayList<>();
        foodOrders.add(foodOrder);
        foodOrders.add(foodOrder2);
        assertEquals(foodOrders.size(), foodOrderService.all().size());
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testDelete() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        int id = foodOrderService.all().get(0).getId();
        assertEquals(EXECUTED, foodOrderService.delete(id));
        assertEquals(0, foodOrderService.all().size());
    }

    /**
     * Tests the deletion of a non-existing instance.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testDeleteIllegal() {
        assertEquals(RESERVATION_NOT_FOUND, foodOrderService.delete(0));
    }

    /**
     * Tests the retrieval of past food orders for the user that sends the request.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetPastFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        assertEquals(Collections.singletonList(foodOrder), foodOrderService.past(request));
    }

    /**
     * Tests the retrieval of past food orders for a non-existent user.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetNonExistentPastFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), foodOrderService.past(request));
    }

    /**
     * Tests the addition of a dishOrder to a food order.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testAddDishOrder() {
        Dish dishA = new Dish("Tosti", menu, 300, "Cooked", "123");
        dishRepository.save(dishA);
        Dish dishB = new Dish("Hamburger", menu, 400, "Grilled", "234");
        dishRepository.save(dishB);
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        foodOrder = foodOrderService.all().get(0);
        foodOrderService.addDishOrder(request, foodOrder.getId(),"Tosti", 1);
        foodOrderService.addDishOrder(request, foodOrder.getId(),"Hamburger", 2);
        Iterator<DishOrder> dishes = foodOrderService.getDishOrders(request, foodOrder.getId()).iterator();
        String dish1 = dishes.next().getDish().getName();
        String dish2 = dishes.next().getDish().getName();
        String swap;
        if (dish2.equals("Hamburger")) {
            swap = dish2;
            dish2 = dish1;
            dish1 = swap;
        }
        assertEquals(dish1, "Hamburger");
        assertEquals(dish2,"Tosti");
    }

    /**
     * Tests the retrieval of future food orders for the user that sends the request.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetFutureFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        assertEquals(Collections.singletonList(foodOrder2), foodOrderService.future(request));
    }

    /**
     * Tests the retrieval of future food orders for a non-existent user.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetNonExistentFutureFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), foodOrderService.future(request));
    }

    /**
     * Tests the retrieval of active food orders for the user that sends the request.
     */
    @Test
    public void testActiveFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        assertEquals(Collections.singletonList(foodOrder2), foodOrderService.active(request));
    }

    /**
     * Tests the retrieval of active food orders for a non-existent user.
     */
    @Test
    public void testGetNonExistentActiveFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        request = new MockHttpServletRequest();
        assertEquals(new ArrayList<>(), foodOrderService.active(request));
    }

    /**
     * Tests the cancellation of food orders for the user that sends the request.
     */
    @Test
    public void testCancelFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        assertNotEquals(new ArrayList<>(), foodOrderService.active(request));
        foodOrderService.cancel(request, foodOrderService.all().get(0).getId());
        assertEquals(new ArrayList<>(), foodOrderService.active(request));
    }

    /**
     * Tests the cancellation of food orders for a non-existent room reservation.
     */
    @Test
    public void testCancelNonExistentFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(ORDER_NOT_FOUND, foodOrderService.cancel(request, 0));
    }

    /**
     * Tests the cancellation of food orders for a non-existent user.
     */
    @Test
    public void testCancelNonExistentUserFoodOrders() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals(WRONG_USER, foodOrderService.cancel(request, foodOrderService.all().get(0).getId()));
    }

    /**
     * Tests the adding of feedback.
     */
    @Test
    public void testAddFeedback() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        foodOrderService.addFeedback(foodOrderService.all().get(0).getId(), true);
        assertTrue(foodOrderService.all().get(0).isFeedbackHasBeenGiven());
    }

    /**
     * Tests the retrieval of past food orders for the restaurant that sends the request.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetPastFoodOrdersForRestaurant() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        assertEquals(Collections.singletonList(foodOrder), foodOrderService.pastForRestaurant(restaurant.getId()));
    }

    /**
     * Tests the retrieval of past food orders for a non-existent restaurant.
     */
    @Test
    @WithMockUser(username = "restaurant2@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetPastFoodOrdersForNonExistentRestaurant() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds);
        assertEquals(new ArrayList<>(), foodOrderService.pastForRestaurant(restaurant.getId()));
    }

    /**
     * Tests the retrieval of future food orders for the restaurant that sends the request.
     */
    @Test
    @WithMockUser(username = "restaurant@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetFutureFoodOrdersForRestaurant() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        assertEquals(Collections.singletonList(foodOrder2), foodOrderService.futureForRestaurant(restaurant.getId()));
    }

    /**
     * Tests the retrieval of future food orders for a non-existent restaurant.
     */
    @Test
    @WithMockUser(username = "restaurant2@tudelft.nl", roles = {"USER", "STAFF", "RESTAURANT"})
    public void testGetFutureFoodOrdersForNonExistentRestaurant() {
        foodOrderService.add(request, restaurant.getId(), deliverLocation.getId(), deliverTimeMilliseconds2);
        assertEquals(new ArrayList<>(), foodOrderService.futureForRestaurant(restaurant.getId()));
    }
}