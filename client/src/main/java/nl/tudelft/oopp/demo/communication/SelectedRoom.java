package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Room;

public class SelectedRoom {
    private static Room selectedRoom;

    public static void setSelectedRoom(Room room) {
        selectedRoom = room;
    }

    public static Room getSelectedRoom() {
        return selectedRoom;
    }
}
