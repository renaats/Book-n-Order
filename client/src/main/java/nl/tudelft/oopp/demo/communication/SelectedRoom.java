package nl.tudelft.oopp.demo.communication;

public class SelectedRoom {
    private static int selectedRoom;

    public static void setSelectedRoom(int id) {
        selectedRoom = id;
    }

    public static int getSelectedRoom() {
        return selectedRoom;
    }
}
