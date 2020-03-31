package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.BuildingHours;
import nl.tudelft.oopp.demo.entities.Dish;
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
     * Current mapper for buildings.
     * @param buildingJson JSON string representation of a building
     * @return Building object.
     */
    public static Building buildingMapper(String buildingJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(buildingJson, Building.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(buildingJson);
        }
        return null;
    }

    /**
     * Maps all building JSONS to a list.
     * @param buildingsJson a JSON string representing a list.
     * @return A list filled with object Buildings.
     */
    public static List<Building> buildingListMapper(String buildingsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(buildingsJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(buildingsJson);
        }
        return null;
    }

    /**
     * Maps JSON to Room entity.
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
     * @param roomsJson a JSON string representing a list.
     * @return A list filled with object Buildings.
     */
    public static List<Room> roomListMapper(String roomsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(roomsJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(roomsJson);
        }
        return null;
    }

    /**
     * Maps a JSON string to an AppUser object.
     * @param appUserJson JSON representation of a String.
     * @return AppUser Object.
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
     * Maps a JSON string to an UserInformation object.
     * @param userInformationJson JSON representation of a String.
     * @return UserInformation Object.
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
     * Maps a JSON string to an Restaurant object.
     * @param restaurantJson JSON representation of a String.
     * @return Restaurant Object.
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
     * Maps all restaurant JSONS to a list.
     * @param restaurantsJson a JSON string representing a list.
     * @return A list filled with object Restaurant.
     */
    public static List<Restaurant> restaurantListMapper(String restaurantsJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(restaurantsJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(restaurantsJson);
        }
        return null;
    }

    /**
     * Maps all restaurant hours JSONS to an object.
     * @param restaurantHoursJson a JSON string.
     * @return Restaurant hour object.
     */
    public static RestaurantHours restaurantHoursMapper(String restaurantHoursJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(restaurantHoursJson, RestaurantHours.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(restaurantHoursJson);
        }
        return null;
    }

    /**
     * Maps all building hours JSONS to an object.
     * @param buildingHourJson a JSON string.
     * @return building hour object.
     */
    public static BuildingHours buildingHoursMapper(String buildingHourJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(buildingHourJson, BuildingHours.class);
        } catch (Exception e) {
            CustomAlert.warningAlert(buildingHourJson);
        }
        return null;
    }

    /**
     * Maps JSON to RoomReservation entity.
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
     * @param roomReservationsJson a JSON string representing a list.
     * @return A list filled with object Room Reservation.
     */
    public static List<RoomReservation> roomReservationsListMapper(String roomReservationsJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(roomReservationsJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(roomReservationsJson);
        }
        return null;
    }

    /**
     * Maps JSON to FoodOrder entity.
     * @param foodOrderJson representation of a food order.
     * @return FoodOrder entity.
     */
    public static FoodOrder foodOrderMapper(String foodOrderJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

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
     * @param foodOrdersJson a JSON string representing a list.
     * @return A list filled with object Food Order
     */
    public static List<FoodOrder> foodOrdersListMapper(String foodOrdersJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(foodOrdersJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(foodOrdersJson);
        }
        return null;
    }

    /**
     * Maps JSON to BikeReservation entity.
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
     * @param bikeReservationsJson a JSON string representing a list.
     * @return A list filled with object Bike Reservation.
     */
    public static List<BikeReservation> bikeReservationsListMapper(String bikeReservationsJson) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // Convert JSON string to Object
            return mapper.readValue(bikeReservationsJson, new TypeReference<>(){});
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    /**
     * Maps all dishes JSONS to a list.
     * @param dishesJson a JSON string representing a list.
     * @return A list filled with object Dish.
     */
    public static List<Dish> dishListMapper(String dishesJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(dishesJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(dishesJson);
        }
        return null;
    }

    /**
     * Maps all menus JSONS to a list.
     * @param menusJson a JSON string representing a list.
     * @return A list filled with object Menu.
     */
    public static List<Menu> menuListMapper(String menusJson) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Object
            return mapper.readValue(menusJson, new TypeReference<>(){});
        } catch (Exception e) {
            CustomAlert.warningAlert(menusJson);
        }
        return null;
    }
}