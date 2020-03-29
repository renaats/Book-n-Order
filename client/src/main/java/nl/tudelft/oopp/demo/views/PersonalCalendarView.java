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
import nl.tudelft.oopp.demo.entities.*;

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
        List<RoomReservation> roomReservationList;
        List<RoomReservation> roomReservationList1;

        try {
            roomReservationList = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(ServerCommunication.getAllPreviousRoomReservations())));
        } catch (NullPointerException e) {
            roomReservationList = new ArrayList<>();
            roomReservationList.add(null);
        }

        try {
            roomReservationList1 = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomReservationsListMapper(ServerCommunication.getAllFutureRoomReservations())));
        } catch (NullPointerException e) {
            roomReservationList1 = new ArrayList<>();
            roomReservationList1.add(null);
        }

        roomReservationList.addAll(roomReservationList1);

        for (RoomReservation reservation : roomReservationList) {
            if (reservation != null && reservation.getAppUser().getEmail().equals(this.currentUser)) {
                Entry<RoomReservation> bookedEntry = new Entry<>("Booking of Room: " + reservation.getRoom().getName());

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getRoom().getBuilding().getName());
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
        List<FoodOrder> foodOrderList;
        List<FoodOrder> foodOrderList1;

        try {
            foodOrderList = new ArrayList<>(Objects.requireNonNull(JsonMapper.foodOrdersListMapper(ServerCommunication.getAllPreviousFoodOrders())));
        } catch (NullPointerException e) {
            foodOrderList = new ArrayList<>();
            foodOrderList.add(null);
        }

        try {
            foodOrderList1 = new ArrayList<>(Objects.requireNonNull(JsonMapper.foodOrdersListMapper(ServerCommunication.getAllFutureFoodOrders())));
        } catch (NullPointerException e) {
            foodOrderList1 = new ArrayList<>();
            foodOrderList1.add(null);
        }

        foodOrderList.addAll(foodOrderList1);
        for (FoodOrder foodOrder: foodOrderList) {
            if (foodOrder != null && foodOrder.getAppUser().getEmail().equals(this.currentUser)) {
                Entry<FoodOrder> bookedEntry = new Entry<>("Order Number: " + foodOrder.getId());

                LocalTime startTime = convertToLocalTime(foodOrder.getDeliveryTime()).minusMinutes(15);
                LocalTime endTime = convertToLocalTime(foodOrder.getDeliveryTime());
                LocalDate date = convertToLocalDate(foodOrder.getDeliveryTime());

                bookedEntry.setLocation(foodOrder.getDeliveryLocation().getName());
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
        List<BikeReservation> bikeReservationList;
        List<BikeReservation> bikeReservationList1;

        try {
            bikeReservationList = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeReservationsListMapper(ServerCommunication.getAllPreviousBikeReservations())));
        } catch (NullPointerException e) {
            bikeReservationList = new ArrayList<>();
            bikeReservationList.add(null);
        }

        try {
            bikeReservationList1 = new ArrayList<>(Objects.requireNonNull(JsonMapper.bikeReservationsListMapper(ServerCommunication.getAllFutureBikeReservations())));
        } catch (NullPointerException e) {
            bikeReservationList1 = new ArrayList<>();
            bikeReservationList1.add(null);
        }

        bikeReservationList.addAll(bikeReservationList1);

        for (BikeReservation reservation : bikeReservationList) {
            if (reservation != null && reservation.getAppUser().getEmail().equals(this.currentUser)) {
                Entry<BikeReservation> bookedEntry = new Entry<>("Booking of Bike: " + reservation.getBike().getId());

                LocalTime startTime = convertToLocalTime(reservation.getFromTime());
                LocalTime endTime = convertToLocalTime(reservation.getToTime());
                LocalDate date = convertToLocalDate(reservation.getFromTime());

                bookedEntry.setLocation(reservation.getFromBuilding().getName());
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