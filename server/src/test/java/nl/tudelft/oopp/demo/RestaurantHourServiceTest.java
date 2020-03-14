package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.services.BuildingService;
import nl.tudelft.oopp.demo.services.RestaurantHourService;
import nl.tudelft.oopp.demo.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    Building building;

    Restaurant restaurant;
    Restaurant restaurant2;

    /** Sets up the classes before executing the tests.
     */
    @BeforeEach
    public void setup() {
        building = new Building();
        building.setName("EWI");
        building.setStreet("Mekelweg");
        building.setHouseNumber(4);

        restaurant = new Restaurant();
        restaurant.setName("Food Station");
        restaurant.setBuilding(building);

        restaurant2 = new Restaurant();
        restaurant2.setName("Hangout");
        restaurant.setBuilding(building);

        assertEquals(201, buildingService.add("EWI", "Mekelweg", 4));
        building = buildingService.find("EWI");

        assertEquals(201, restaurantService.add(building.getId(), "Food Station"));
        assertEquals(201, restaurantService.add(building.getId(), "Hangout"));

        restaurant = restaurantService.find("Food Station");
        restaurant2 = restaurantService.find("Hangout");

        restaurantHours = new RestaurantHours(1, restaurant, LocalTime.ofSecondOfDay(1000), LocalTime.ofSecondOfDay(3000));
        restaurantHours2 = new RestaurantHours(2, restaurant2, LocalTime.ofSecondOfDay(3000), LocalTime.ofSecondOfDay(4000));
    }

    @Test
    public void testConstructor() {
        assertNotNull(restaurantHourService);
    }

    @Test
    public void testCreate() {
        assertEquals(201, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        assertEquals(Collections.singletonList(restaurantHours), restaurantHourService.all());
    }

    @Test
    public void testAll() {
        Iterator<RestaurantHours> iterator = restaurantHourService.all().iterator();
        assertFalse(iterator.hasNext());
        assertEquals(201, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        iterator = restaurantHourService.all().iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFind() {
        assertEquals(201, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        restaurantHours = restaurantHourService.all().iterator().next();
        assertEquals(restaurantHours, restaurantHourService.find(restaurantHours.getRestaurant().getId(), restaurantHours.getDay()));
        assertNull(restaurantHourService.find(5, 1));
    }

    @Test
    public void testUpdate() {
        assertEquals(201, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        assertEquals(201, restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000));

        List<RestaurantHours> restaurantHourList = new ArrayList<>();
        restaurantHourService.all().forEach(restaurantHourList::add);
        assertEquals(2, restaurantHourList.size());
        assertEquals(416, restaurantHourService.update(1234, "attr", "val"));
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), restaurantHours.getDay()),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), restaurantHours2.getDay()));

        assertEquals(201, restaurantHourService.update(restaurantHours2.getId(), "starttimes",
                ((Integer) restaurantHours.getStartTime().toSecondOfDay()).toString()));

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), restaurantHours.getDay()),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), restaurantHours2.getDay()));

        assertEquals(201, restaurantHourService.update(restaurantHours2.getId(), "endtimes",
                ((Integer) restaurantHours.getEndTime().toSecondOfDay()).toString()));

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), restaurantHours.getDay()),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), restaurantHours2.getDay()));

        assertEquals(201, restaurantHourService.update(restaurantHours2.getId(), "restaurantid",
                ((Integer) restaurantHours.getRestaurant().getId()).toString()));

        assertNotEquals(restaurantHourService.find(restaurantHours.getRestaurant().getId(), restaurantHours.getDay()),
                restaurantHourService.find(restaurantHours2.getRestaurant().getId(), restaurantHours2.getDay()));

        assertEquals(427, restaurantHourService.update(restaurantHours2.getId(), "day", ((Integer) restaurantHours.getDay()).toString()));
    }

    @Test
    public void testDelete() {
        assertEquals(201, restaurantHourService.add(restaurant.getId(), restaurantHours.getDay(), 1000, 3000));
        assertEquals(201, restaurantHourService.add(restaurant2.getId(), restaurantHours2.getDay(), 2000, 4000));

        List<RestaurantHours> restaurantHourList = new ArrayList<>();
        restaurantHourService.all().forEach(restaurantHourList::add);
        assertEquals(2, restaurantHourList.size());
        restaurantHours = restaurantHourList.get(0);
        restaurantHours2 = restaurantHourList.get(1);
        restaurantHourService.delete(restaurantHourList.get(0).getRestaurant().getId(), restaurantHourList.get(0).getDay());

        restaurantHourList = new ArrayList<>();
        restaurantHourService.all().forEach(restaurantHourList::add);
        assertEquals(1, restaurantHourList.size());
        assertNull(restaurantHourService.find(restaurantHours.getRestaurant().getId(), restaurantHours.getDay()));
        assertNotNull(restaurantHourService.find(restaurantHours2.getRestaurant().getId(), restaurantHours2.getDay()));
    }

}