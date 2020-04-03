package nl.tudelft.oopp.demo.entities;

public class SelectedRoom {
    private static int selectedRoom;

    public static void setSelectedRoom(int id) {
        selectedRoom = id;
    }

    public static int getSelectedRoom() {
        return selectedRoom;
    }
}
