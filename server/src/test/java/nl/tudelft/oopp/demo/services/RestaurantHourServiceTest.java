package nl.tudelft.oopp.demo.services;

import static nl.tudelft.oopp.demo.config.Constants.ADDED;
import static nl.tudelft.oopp.demo.config.Constants.ATTRIBUTE_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.BUILDING_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.DUPLICATE_DAY;
import static nl.tudelft.oopp.demo.config.Constants.END_BEFORE_START;
import static nl.tudelft.oopp.demo.config.Constants.EXECUTED;
import static nl.tudelft.oopp.demo.config.Constants.ID_NOT_FOUND;
import static nl.tudelft.oopp.demo.config.Constants.INVALID_DAY;
import static nl.tudelft.oopp.demo.config.Constants.NOT_FOUND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests the RestaurantHourService service.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RestaurantHourServiceTest {
    @TestConfiguration
    static class RestaurantServiceTestConfiguration {
        @Bean
        public RestaurantService restaurantService() {
            return new RestaurantService();
        }
    }

    @TestConfiguration
    static class RestaurantHourServiceTestConfiguration {
        @Bean
        public RestaurantHourService restaurantHourService() {
            return new RestaurantHourService();
        }
    }

    @TestConfiguration
    static class BuildingServiceTestConfiguration {
        @Bean
        public BuildingService buildingService() {
            return new BuildingService();
        }
    }

    @Autowired
    RestaurantHourService restaurantHourService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    BuildingService buildingService;

    RestaurantHours restaurantHours;
    RestaurantHours restaurantHours2;
    RestaurantHours restaurantHoursSpecial;
    RestaurantHours restaurantHoursSpecial2;

    Building building;

    Restaurant restaurant;
    Restaurant restaurant2;

    /**
     * Sets up the entities and saves them via a service before executing every test.
     */
    @BeforeEach
    public void setup() {
        building = new Building("EWI", "Mekelweg", "EWI", 4);

        restaurant = new Restaurant(building, "Food Station", "");

        restaurant2 = new Restaurant(building, "Hangout", "");

        buildingService.add("EWI", "Mekelweg", "EWI", 4);
        building = buildingService.find("EWI");

        restaurantService.add(building.getId(), "Food Station", "");
        restaurantService.add(building.getId(), "Hangout", "");

        restaurant = restaurantService.find("Food Station");
        restaurant2 = restaurantService.find("Hangout");

        restaurantHours = new RestaurantHours(1, restaurant, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        restaurantHours2 = new RestaurantHours(2, restaurant2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
        restaurantHoursSpecial =
                new RestaurantHours(BuildingHourService.parse(939600000), restaurant, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        restaurantHoursSpecial2 =
                new RestaurantHours(BuildingHourService.parse(1026000000), restaurant2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
    }

    /**
     * Tests the constructor creating a new instance of the entity.
     */
    @Test
    public void testConstructor() {
        assertNotNull(restaurantHourService);
    }

    /**
     * Tests the creation of an instance with an invalid building id.
     */
    @Test
    public void testInvalidBuilding() {
        assertEquals(BUILDING_NOT_FOUND, restaurantHourService.add(0, restaurantHours.getDay(), 1000, 3000));
    }

    /**
     * Tests the creation of an instance with a day not between 1 and 7.
     */
    @Test
    public void testInvalidDay() {
        assertEquals(INVALID_DAY, restaurantHourService.add(restaurant.getId(), 0, 1000, 3000));
    }

    /**
     * Tests the creation of an instance with the end time before the start time.
     */
    @Test
    public void testEndBeforeStart() {
        assertEquals(END_BEFORE_START, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 3000, 1000));
    }

    /**
     * Tests the creation of an instance that already exists.
     */
    @Test
    public void testSameBuildingTwice() {
        assertEquals(ADDED, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        assertEquals(DUPLICATE_DAY, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
    }

    /**
     * Tests the saving and retrieval of an instance of RoomHourService.
     */
    @Test
    public void testRetrieveOne() {
        assertEquals(ADDED, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        assertEquals(Collections.singletonList(restaurantHours), restaurantHourService.all());
    }

    /**
     * Tests the iterator of an empty list of RestaurantHours.
     */
    @Test
    public void testEmptyIterator() {
        Iterator<RestaurantHours> iterator = restaurantHourService.all().iterator();
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the iterator of a non-empty list of RestaurantHours.
     */
    @Test
    public void testNonEmptyIterator() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        Iterator<RestaurantHours> iterator = restaurantHourService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the search for a non-existing object.
     */
    @Test
    public void testFindNonExisting() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHours = restaurantHourService.all().iterator().next();
        assertNull(restaurantHourService.find(5, 1));
    }

    /**
     * Tests the retrieval of multiple instances of RestaurantHours.
     */
    @Test
    public void testRetrieveMultiple() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        assertEquals(2, restaurantHourList.size());

    }

    /**
     * Tests the update operation on a non-existent object.
     */
    @Test
    public void testUpdateNonExisting() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000);
        assertEquals(ID_NOT_FOUND, restaurantHourService.update(1234, "attr", "val"));
    }

    /**
     * Tests the update operation on a non-existent attribute.
     */
    @Test
    public void testUpdateNonExistingAttribute() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        int id = restaurantHourService.all().get(0).getId();
        assertEquals(ATTRIBUTE_NOT_FOUND, restaurantHourService.update(id, "a", "a"));
    }

    /**
     * Tests the change of the day by using the service.
     */
    @Test
    public void testChangeDay() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 1000, 3000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNull(restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 939600000));
        restaurantHourService.update(restaurantHours2.getId(), "day", ((Long) 939600000L).toString());

        assertEquals(BuildingHourService.parse(939600000), restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 939600000).getDay());
    }

    /**
     * Tests the change of the restaurant by using the service.
     */
    @Test
    public void testChangeRestaurant() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 1000, 3000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 1026000000));

        restaurantHourService.update(restaurantHours2.getId(), "restaurantid", ((Integer) restaurantHours.getRestaurant().getId()).toString());

        assertEquals(restaurant, restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000).getRestaurant());
    }

    /**
     * Tests the change of the start time by using the service.
     */
    @Test
    public void testChangeStartTime() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 3000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 1026000000));

        restaurantHourService.update(restaurantHours2.getId(), "starttimes", ((Integer) restaurantHours.getStartTime().toSecondOfDay()).toString());

        assertEquals(restaurantHours.getStartTime(),
                restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000).getStartTime());
    }

    /**
     * Tests the change of the end time by using the service.
     */
    @Test
    public void testChangeEndTime() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 1000, 4000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 1026000000));

        restaurantHourService.update(restaurantHours2.getId(), "endtimes", ((Integer) restaurantHours.getEndTime().toSecondOfDay()).toString());

        assertEquals(restaurantHours.getEndTime(), restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000).getEndTime());
    }

    /**
     * Tests the rejection of the modification of an instance that would make it equal to another instance.
     */
    @Test
    public void testRejectEqualInstances() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 1000, 3000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 1026000000));

        restaurantHourService.update(restaurantHours2.getId(), "restaurantid", ((Integer) restaurantHours.getRestaurant().getId()).toString());

        assertEquals(DUPLICATE_DAY, restaurantHourService.update(restaurantHours2.getId(), "day", ((Long) restaurantHours.getDay()).toString()));
    }

    /**
     * Tests the deletion of an instance.
     */
    @Test
    public void testDeleteOne() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertEquals(EXECUTED, restaurantHourService.delete(restaurantHourList.get(0).getRestaurant().getId(), restaurantHourList.get(0).getDay()));

        restaurantHourList = new ArrayList<>(restaurantHourService.all());
        assertEquals(1, restaurantHourList.size());
    }

    /**
     * Tests the deletion of the correct instance.
     */
    @Test
    public void testDeleteSearch() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        restaurantHourService.delete(restaurantHourList.get(0).getRestaurant().getId(), restaurantHourList.get(0).getDay());

        assertNull(restaurantHourService.find(restaurantHours.getRestaurant().getId(), 939600000));
        assertNotNull(restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 1026000000));
    }


    /**
     * Tests the deletion of a non-existent instance.
     */
    @Test
    public void testDeleteNonExistent() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertEquals(NOT_FOUND, restaurantHourService.delete(restaurantHourList.get(0).getRestaurant().getId(), restaurantHourList.get(1).getDay()));
    }

    /**
     * Tests the saving and retrieval of a special hour instance of RestaurantHours.
     */
    @Test
    public void testRetrieveSpecial() {
        assertEquals(ADDED, restaurantHourService.add(restaurant.getId(), 939600000, 1000, 3000));
        assertEquals(Collections.singletonList(restaurantHoursSpecial), restaurantHourService.all());
    }

    /**
     * Tests the saving and retrieval of an instance of RestaurantHours by restaurant id and day.
     */
    @Test
    public void testFindByRestaurantAndDay() {
        assertEquals(ADDED, restaurantHourService.add(restaurant.getId(), restaurantHoursSpecial.getDay(), 1000, 3000));
        assertEquals(restaurantHoursSpecial, restaurantHourService.findAdmin(restaurant.getId(), restaurantHoursSpecial.getDay()));
    }

    /**
     * Tests the change of the day to a special day by using the service.
     */
    @Test
    public void testChangeDaySpecial() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000);

        List<RestaurantHours> restaurantHourList = new ArrayList<>(restaurantHourService.all());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNull(restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 939600000));
        restaurantHourService.update(restaurantHours2.getId(), "day", ((Long) 939600000L).toString());
        assertEquals(BuildingHourService.parse(939600000), restaurantHourService.find(restaurantHours2.getRestaurant().getId(), 939600000).getDay());
    }

    /**
     * Tests the deletion of a special hour instance.
     */
    @Test
    public void testDeleteSpecial() {
        restaurantHourService.add(restaurant.getId(), 939600000, 1000, 3000);
        restaurantHoursSpecial = restaurantHourService.all().get(0);
        assertEquals(EXECUTED, restaurantHourService.delete(restaurantHoursSpecial.getRestaurant().getId(), restaurantHoursSpecial.getDay()));
        assertEquals(0, restaurantHourService.all().size());
    }

    /**
     * Tests the returned object when the date has both normal and special hours.
     */
    @Test
    public void testHasBothTypes() {
        restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000);
        assertEquals(restaurantHours, restaurantHourService.find(restaurant.getId(), 939600000));
        restaurantHourService.add(restaurant.getId(), 939600000, 1000, 3000);
        assertEquals(restaurantHoursSpecial, restaurantHourService.find(restaurant.getId(), 939600000));
    }

    /**
     * Tests the adding of RestaurantHours for the current day.
     */
    @Test
    public void testToday() {
        Date date = new Date();
        assertNull(restaurantHourService.find(restaurant.getId(), date.getTime()));
        restaurantHourService.add(restaurant.getId(), date.getTime(), 1000, 3000);
        assertNotNull(restaurantHourService.find(restaurant.getId(), date.getTime()));
    }
}