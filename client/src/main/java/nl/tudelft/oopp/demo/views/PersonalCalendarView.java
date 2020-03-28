package nl.tudelft.oopp.demo.views;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.RoomReservation;

public class PersonalCalendarView extends CalendarView {

    private String currentUser;

    Calendar bookedRooms = new Calendar("Room Bookings");
    Calendar orderedFood = new Calendar("Food Orders");
    Calendar rentedBikes = new Calendar("Bike Rent");

    /**
     * Constuctor for the Personal Calendar View
     */
    public PersonalCalendarView(String currentUser) {
        this.currentUser = currentUser;
        displayCalendars();
        loadRoomReservations();
        loadBikeReservations();
        loadFoodOrders();
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadRoomReservations() {
        List<RoomReservation> roomReservationList =
                new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(ServerCommunication.getRoomReservations())));
        for (RoomReservation reservation : roomReservationList) {
            if (reservation.getAppUser().getEmail().equals(this.currentUser)) {
            Entry<RoomReservation> bookedEntry = new Entry<>("Booking of " + reservation.getRoom().getName());

            LocalTime startTime = convertToLocalTime(reservation.getFromTime());
            LocalTime endTime = convertToLocalTime(reservation.getToTime());
            LocalDate date = convertToLocalDate(reservation.getFromTime());

            bookedEntry.setLocation(reservation.getRoom().getBuilding().toString());
            bookedEntry.setInterval(date);
            bookedEntry.setInterval(startTime, endTime);
            bookedRooms.addEntry(bookedEntry);
            }
        }
    }

    /**
     * Methods that loads food orders of personal user
     */
    public void loadFoodOrders() {
        List<FoodOrder> foodOrdersList =
                new ArrayList<>(Objects.requireNonNull(JsonMapper.foodOrdersListMapper(ServerCommunication.getAllFoodOrders())));
        for(FoodOrder foodOrder: foodOrdersList) {
            if (foodOrder.getAppUser().getEmail().equals(this.currentUser)) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Order Number: " + foodOrder.getId());

                LocalTime startTime = convertToLocalTime(foodOrder.getDeliveryTime());
                LocalTime endTime = convertToLocalTime(foodOrder.getDeliveryTime());
                LocalDate date = convertToLocalDate(foodOrder.getDeliveryTime());

                bookedEntry.setLocation(foodOrder.getDeliveryLocation().toString());
                bookedEntry.setInterval(date);
                bookedEntry.setInterval(startTime, endTime);
                orderedFood.addEntry(bookedEntry);
            }
        }
    }

    /**
     * Methods that loads room reservations of personal user
     */
    public void loadBikeReservations() {
        List<BikeReservation> bikeReservationList =
                new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeReservationsListMapper(ServerCommunication.getAllBikeReservations())));
        for (BikeReservation reservation : bikeReservationList) {
            if (reservation.getAppUser().getEmail().equals(this.currentUser)) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Booking of " + String.valueOf(reservation.getBike().getId()));

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getFromBuilding().toString());
                bookedEntry.setInterval(date);
                bookedEntry.setInterval(startTime, endTime);
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

        bookedRooms.setReadOnly(true);
        orderedFood.setReadOnly(true);
        rentedBikes.setReadOnly(true);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(bookedRooms, orderedFood, rentedBikes);
        this.getCalendarSources().add(myCalendarSource);

        CalendarView view = new CalendarView();
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