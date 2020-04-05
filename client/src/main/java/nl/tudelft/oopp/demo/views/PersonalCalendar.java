package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.sun.javafx.scene.control.IntegerField;
import nl.tudelft.oopp.demo.communication.*;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.errors.CustomAlert;

public class PersonalCalendar extends CalendarView {

    Calendar bookedRooms = new Calendar("Room Bookings");
    Calendar orderedFood = new Calendar("Food Orders");
    Calendar rentedBikes = new Calendar("Bike Rentals");

    String attemptedDelete = "empty";

    /**
     * Constuctor for the Personal Calendar View
     */
    public PersonalCalendar() {
        displayCalendars();
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList;
        try {
            String roomReservationJson = RoomServerCommunication.getAllActiveRoomReservations();
            roomReservationList = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(roomReservationJson)));
        } catch (NullPointerException e) {
            roomReservationList = new ArrayList<>();
            roomReservationList.add(null);
        }

        for (RoomReservation reservation : roomReservationList) {
            if (reservation != null) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Booking of Room: " + reservation.getRoom().getName());

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getRoom().getBuilding().getName());
                bookedEntry.setInterval(date);
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
                    JsonMapper.foodOrdersListMapper(DishServerCommunication.getAllActiveFoodOrders())));
        } catch (NullPointerException e) {
            foodOrderList = new ArrayList<>();
            foodOrderList.add(null);
        }

        for (FoodOrder foodOrder: foodOrderList) {
            if (foodOrder != null) {
                Entry<FoodOrder> bookedEntry = new Entry<>("Order Number: " + foodOrder.getId());

                LocalTime startTime = convertToLocalTime(foodOrder.getDeliveryTime()).minusMinutes(15);
                LocalTime endTime = convertToLocalTime(foodOrder.getDeliveryTime());
                LocalDate date = convertToLocalDate(foodOrder.getDeliveryTime());

                bookedEntry.setLocation(foodOrder.getDeliveryLocation().getName());
                bookedEntry.setInterval(date);
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
        } catch (NullPointerException e) {
            bikeReservationList = new ArrayList<>();
            bikeReservationList.add(null);
        }

        for (BikeReservation reservation : bikeReservationList) {
            if (reservation != null) {
                Entry<BikeReservation> bookedEntry = new Entry<>("Booking of Bike: " + reservation.getBike().getId());

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getFromBuilding().getName());
                bookedEntry.setInterval(date);
                bookedEntry.setInterval(startTime, endTime);
                bookedEntry.setId(String.valueOf(reservation.getId()));
                rentedBikes.addEntry(bookedEntry);
            }
        }
    }

    /**
     * Handles the event of entry removal. Cancels the reservation/order of the entry that was removed.
     * @param event = the calendar event
     */
    public void roomReservationCancellationHandler(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            if (attemptedDelete.equals(event.getEntry().getId())) {
                DishServerCommunication.cancelFoodOrder(Integer.parseInt(event.getEntry().getId()));
            } else {
                attemptedDelete = event.getEntry().getId();
                CustomAlert.warningAlert("You are about to cancel your reservation. Try again to complete cancellation.");
                bookedRooms.addEntry(event.getEntry());
                attemptedDelete = event.getEntry().getId();
            }
        }
        if (event.isEntryAdded()) {
            int i = 1;
        }
    }

    /**
     * Handles the event of entry removal. Cancels the reservation/order of the entry that was removed.
     * @param event = the calendar event
     */
    public void foodOrderCancellationHandler(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            if (attemptedDelete.equals(event.getEntry().getId())) {
                DishServerCommunication.cancelFoodOrder(Integer.parseInt(event.getEntry().getId()));
            } else {
                attemptedDelete = event.getEntry().getId();
                event.getEntry().getCalendar().addEntry(event.getEntry());
                CustomAlert.warningAlert("You are about to cancel your order. Try again to complete cancellation.");
            }
        }
    }

    /**
     * Handles the event of entry removal. Cancels the reservation/order of the entry that was removed.
     * @param event = the calendar event
     */
    public void bikeReservationCancellationHandler(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            if (attemptedDelete.equals(event.getEntry().getId())) {
                DishServerCommunication.cancelFoodOrder(Integer.parseInt(event.getEntry().getId()));
            } else {
                attemptedDelete = event.getEntry().getId();
                event.getEntry().getCalendar().addEntry(event.getEntry());
                CustomAlert.warningAlert("You are about to cancel your reservation. Try again to complete cancellation.");
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

        bookedRooms.addEventHandler(this::roomReservationCancellationHandler);
        orderedFood.addEventHandler(this::foodOrderCancellationHandler);
        rentedBikes.addEventHandler(this::bikeReservationCancellationHandler);
    }

    public Date convertToDate(LocalTime time, LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
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