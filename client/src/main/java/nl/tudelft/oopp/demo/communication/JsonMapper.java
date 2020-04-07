package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Allergy;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Bike;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.Menu;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantHours;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.user.UserInformation;

/**
 * Maps Json server responses into objects in the Entities package
 */
public class JsonMapper {

    /**
     * Current mapper for buildings
     *
     * @param buildingJson JSON string representation of a building
     * @return Building object
     */
    public static Building buildingMapper(String buildingJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(buildingJson, Building.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Current mapper for menus
     *
     * @param menuJson JSON string representation of a menu
     * @return Menu object
     */
    public static Menu menuMapper(String menuJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(menuJson, Menu.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all building JSONS to a list.
     *
     * @param buildingsJson a JSON string representing a list.
     * @return A list filled with Buildings objects
     */
    public static List<Building> buildingListMapper(String buildingsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(buildingsJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all allergies in the JSON to a list.
     *
     * @param allergiesJson a JSON string representing a list.
     * @return A list filled with Allergies objects
     */
    public static List<Allergy> allergiesListMapper(String allergiesJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(allergiesJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps JSON to Room entity.
     *
     * @param roomJson JSON representation of a room.
     * @return Room entity.
     */
    public static Room roomMapper(String roomJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(roomJson, Room.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(roomJson);
        }
        return null;
    }

    /**
     * Maps all room JSONS to a list.
     *
     * @param roomsJson a JSON string representing a list.
     * @return A list filled with object room
     */
    public static List<Room> roomListMapper(String roomsJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(roomsJson, new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps a JSON string to an AppUser object
     *
     * @param appUserJson JSON representation of a String
     * @return AppUser Object
     */
    public static AppUser appUserMapper(String appUserJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(appUserJson, AppUser.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(appUserJson);
        }
        return null;
    }

    /**
     * Maps a JSON string to an UserInformation object
     *
     * @param userInformationJson JSON representation of a String
     * @return UserInformation Object
     */
    public static UserInformation userInformationMapper(String userInformationJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(userInformationJson, UserInformation.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(userInformationJson);
        }
        return null;
    }

    /**
     * Maps a JSON string to an Restaurant object
     *
     * @param restaurantJson JSON representation of a String
     * @return Restaurant Object
     */
    public static Restaurant restaurantMapper(String restaurantJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(restaurantJson, Restaurant.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(restaurantJson);
        }
        return null;
    }

    /**
     * Maps a JSON string to an Restaurant object and doesn't throw a warning if it gets a null
     *
     * @param restaurantsJson JSON representation of a String
     * @return Restaurant Object List
     */
    public static List<Restaurant> ownRestaurantMapper(String restaurantsJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(restaurantsJson, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all restaurant JSONS to a list.
     *
     * @param restaurantsJson a JSON string representing a list.
     * @return A list filled with object Restaurant
     */
    public static List<Restaurant> restaurantListMapper(String restaurantsJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(restaurantsJson, new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all restaurant hours JSONS to an object.
     *
     * @param restaurantHoursJson a JSON string
     * @return Restaurant hour object
     */
    public static RestaurantHours restaurantHoursMapper(String restaurantHoursJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(restaurantHoursJson, RestaurantHours.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all building hours JSONS to an object.
     *
     * @param buildingHourJson a JSON string
     * @return building hour object
     */
    public static BuildingHours buildingHoursMapper(String buildingHourJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(buildingHourJson, BuildingHours.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps JSON to RoomReservation entity.
     *
     * @param roomReservationJson representation of a room.
     * @return RoomReservation entity.
     */
    public static RoomReservation roomReservationMapper(String roomReservationJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(roomReservationJson, RoomReservation.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(roomReservationJson);
        }
        return null;
    }

    /**
     * Maps all Room Reservation JSONS to a list.
     *
     * @param roomReservationsJson a JSON string representing a list.
     * @return A list filled with object Room Reservation
     */
    public static List<RoomReservation> roomReservationsListMapper(String roomReservationsJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(roomReservationsJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps JSON to FoodOrder entity.
     *
     * @param foodOrderJson representation of a food order.
     * @return FoodOrder entity.
     */
    public static FoodOrder foodOrderMapper(String foodOrderJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(foodOrderJson, FoodOrder.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(foodOrderJson);
        }
        return null;
    }

    /**
     * Maps all Food Orders JSONS to a list.
     *
     * @param foodOrdersJson a JSON string representing a list.
     * @return A list filled with object Food Order.
     */
    public static List<FoodOrder> foodOrdersListMapper(String foodOrdersJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(foodOrdersJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps JSON to Bike entity.
     *
     * @param bikeJson JSON representation of a bike.
     * @return Bike entity.
     */
    public static Bike bikeMapper(String bikeJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(bikeJson, Bike.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(bikeJson);
        }
        return null;
    }

    /**
     * Maps all room JSONS to a list.
     *
     * @param bikesJson a JSON string representing a list.
     * @return A list filled with object bike
     */
    public static List<Bike> bikeListMapper(String bikesJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(bikesJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps JSON to BikeReservation entity.
     *
     * @param bikeReservationJson representation of a bike reservation.
     * @return BikeReservation entity.
     */
    public static BikeReservation bikeReservationMapper(String bikeReservationJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(bikeReservationJson, BikeReservation.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(bikeReservationJson);
        }
        return null;
    }

    /**
     * Maps all Bike Reservation JSONS to a list.
     *
     * @param bikeReservationsJson a JSON string representing a list.
     * @return A list filled with object Bike Reservation.
     */
    public static List<BikeReservation> bikeReservationsListMapper(String bikeReservationsJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(bikeReservationsJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all menu JSONS to a list.
     *
     * @param menusJson a JSON string representing a list.
     * @return A list filled with object Menus.
     */
    public static List<Menu> menuListMapper(String menusJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(menusJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all dish JSONS to a list.
     *
     * @param dishesJson a JSON string representing a list.
     * @return A list filled with object dishes.
     */
    public static List<Dish> dishListMapper(String dishesJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(dishesJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all dish order JSONS to a list.
     * @param dishOrdersJson a JSON string representing a list.
     * @return A list filled with object dish orders.
     */
    public static List<DishOrder> dishOrderListMapper(String dishOrdersJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(dishOrdersJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Maps all food orders JSONS to a list.
     * @param allFoodOrdersJson a JSON string representing a list.
     * @return A list filled with object Food orders.
     */
    public static List<FoodOrder> foodOrderList(String allFoodOrdersJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(allFoodOrdersJson, new TypeReference<List<FoodOrder>>() {
            });
        } catch (Exception e) {
            CustomAlert.warningAlert(allFoodOrdersJson);
        }
        return null;
    }
}