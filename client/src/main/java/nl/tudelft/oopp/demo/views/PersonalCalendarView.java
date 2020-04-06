package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import nl.tudelft.oopp.demo.communication.BikeServerCommunication;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.RestaurantServerCommunication;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.errors.ErrorMessages;

public class PersonalCalendarView extends CalendarView {

    Calendar bookedRooms = new Calendar("Room Bookings");
    Calendar orderedFood = new Calendar("Food Orders");
    Calendar rentedBikes = new Calendar("Bike Rentals");

    String attemptedDelete = "empty";

    /**
     * Constructor for the Personal Calendar View
     */
    public PersonalCalendarView() {
        displayCalendars();
        bookedRooms.addEventHandler(this::roomReservationCancellationHandler);
        orderedFood.addEventHandler(this::foodOrderCancellationHandler);
        rentedBikes.addEventHandler(this::bikeReservationCancellationHandler);
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList;
        try {
            String roomReservationJson = RoomServerCommunication.getAllActiveRoomReservations();
            roomReservationList = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(roomReservationJson)));
        } catch (NullPointerException | JsonProcessingException e) {
            roomReservationList = new ArrayList<>();
            roomReservationList.add(null);
        }

        for (RoomReservation reservation : roomReservationList) {
            if (reservation != null) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Booking of Room: " + reservation.getRoom().getName());

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate fromDate = convertToLocalDate(reservation.getFromTime());
                LocalDate toDate = convertToLocalDate(reservation.getToTime());

                bookedEntry.setLocation(reservation.getRoom().getBuilding().getName());
                bookedEntry.setInterval(fromDate, toDate);
                bookedEntry.setInterval(startTime, endTime);
                bookedEntry.setId(String.valueOf(reservation.getId()));
                bookedRooms.addEntry(bookedEntry);
            }
        }
    }

    /**
     * Methods that loads food orders of personal user
     */
    public void loadFoodOrders() {
        List<FoodOrder> foodOrderList;

        try {
            foodOrderList = new ArrayList<>(Objects.requireNonNull(
                    JsonMapper.foodOrdersListMapper(RestaurantServerCommunication.getAllActiveFoodOrders())));
        } catch (Exception e) {
            foodOrderList = new ArrayList<>();
            foodOrderList.add(null);
        }

        for (FoodOrder foodOrder: foodOrderList) {
            if (foodOrder != null) {
                Entry<FoodOrder> bookedEntry = new Entry<>("Order Number: " + foodOrder.getId());

                LocalTime startTime = convertToLocalTime(foodOrder.getDeliveryTime()).minusMinutes(15);
                LocalTime endTime = convertToLocalTime(foodOrder.getDeliveryTime());
                LocalDate date = convertToLocalDate(foodOrder.getDeliveryTime());

                if (foodOrder.getDeliveryLocation() == null) {
                    bookedEntry.setLocation(foodOrder.getRestaurant().getBuilding().getName());
                } else {
                    bookedEntry.setLocation(foodOrder.getDeliveryLocation().getName());
                }

                if (startTime.isAfter(endTime)) {
                    bookedEntry.setInterval(date.minusDays(1), date);
                } else {
                    bookedEntry.setInterval(date);
                }
                bookedEntry.setInterval(startTime, endTime);
                bookedEntry.setId(String.valueOf(foodOrder.getId()));
                orderedFood.addEntry(bookedEntry);
            }
        }
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadBikeReservations() {
        List<BikeReservation> bikeReservationList;

        try {
            String bikeReservationJson = BikeServerCommunication.getAllActiveBikeReservations();
            bikeReservationList = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeReservationsListMapper(bikeReservationJson)));
        } catch (NullPointerException | JsonProcessingException e) {
            bikeReservationList = new ArrayList<>();
            bikeReservationList.add(null);
        }

        for (BikeReservation reservation : bikeReservationList) {
            if (reservation != null) {
                Entry<BikeReservation> bookedEntry = new Entry<>("Booking of Bike: " + reservation.getBike().getId());

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate fromDate = convertToLocalDate(reservation.getFromTime());
                LocalDate toDate = convertToLocalDate(reservation.getToTime());

                bookedEntry.setLocation(reservation.getFromBuilding().getName());
                bookedEntry.setInterval(fromDate, toDate);
                bookedEntry.setInterval(startTime, endTime);
                bookedEntry.setId(String.valueOf(reservation.getId()));
                rentedBikes.addEntry(bookedEntry);
            }
        }
    }

    /**
     * Method that displays the different calendars.
     */
    public void displayCalendars() {
        bookedRooms.setStyle(Calendar.Style.STYLE2);
        orderedFood.setStyle(Calendar.Style.STYLE3);
        rentedBikes.setStyle(Calendar.Style.STYLE4);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(bookedRooms, orderedFood, rentedBikes);
        this.getCalendarSources().add(myCalendarSource);
    }

    /**
     * Handles the event of entry removal. Cancels the room reservation of the entry that was removed.
     * @param event = the calendar event
     */
    public void roomReservationCancellationHandler(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            RoomServerCommunication.cancelRoomReservation(Integer.parseInt(event.getEntry().getId()));
            CustomAlert.informationAlert(ErrorMessages.getErrorMessage(200));
        }
    }

    /**
     * Handles the event of entry removal. Cancels the food order of the entry that was removed.
     * @param event = the calendar event
     */
    public void foodOrderCancellationHandler(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            RestaurantServerCommunication.cancelFoodOrder(Integer.parseInt(event.getEntry().getId()));
            CustomAlert.informationAlert(ErrorMessages.getErrorMessage(200));
        }
    }

    /**
     * Handles the event of entry removal. Cancels the bike reservation of the entry that was removed.
     * @param event = the calendar event
     */
    public void bikeReservationCancellationHandler(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            BikeServerCommunication.cancelBikeReservation(Integer.parseInt(event.getEntry().getId()));
            CustomAlert.informationAlert(ErrorMessages.getErrorMessage(200));
        }
    }

    public LocalTime convertToLocalTime(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();
    }

    public LocalDate convertToLocalDate(Date date) {
        Instant instant1 = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDate();
    }
}