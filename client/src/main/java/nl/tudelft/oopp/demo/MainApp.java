package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

public class MainApp {
    /**
     * runs the client side app
     */
    public static void main(String[] args) {
        System.out.println(ServerCommunication.getFoodOrders());
    }
}
