package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.JsonMapper;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.errors.CustomAlert;
import nl.tudelft.oopp.demo.views.ApplicationDisplay;

import javax.crypto.spec.PSource;

/**
 * Loads the correct content into the FXML objects that need to display server information and
 * controls all the user inputs made through the GUI in the "DatabaseEditRooms.fxml" file
 */
public class DatabaseRoomMenuController implements Initializable {

    private final ObservableList<Room> roomResult = FXCollections.observableArrayList();
    private final ObservableList<String> studyList = FXCollections.observableArrayList();
    private final ObservableList<String> statusList = FXCollections.observableArrayList();

    @FXML
    private TableView<Room> table;
    @FXML
    private Text pagesText;
    @FXML
    private ToggleButton projectorToggle;
    @FXML
    private ToggleButton screenToggle;
    @FXML
    private TableColumn<Room, String> colName;
    @FXML
    private TableColumn<Room, String> colBuilding;
    @FXML
    private TableColumn<Room, Integer> colStudy;
    @FXML
    private TableColumn<Room, Integer> colStatus;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField idFieldRead;
    @FXML
    private TextField nameFieldRead;
    @FXML
    private TextField buildingFieldRead;
    @FXML
    private TextField capacityReadField;
    @FXML
    private TextField plugsReadField;
    @FXML
    private ChoiceBox<String> studyChoiceBox;
    @FXML
    private ChoiceBox<String> statusChoiceBox;

    private int pageNumber;
    private double totalPages;
    private Button deleteButton;
    private boolean projectorToggleFlag;
    private boolean screenToggleFlag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<>("getBuildingName"));
        colStudy.setCellValueFactory(new PropertyValueFactory<>("studySpecific"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        if (pageNumber == 0) {
            pageNumber++;
        }

        idFieldRead.setDisable(true);
        buildingFieldRead.setDisable(true);
        retrieveAllRooms();
        tableSelectMethod();
        loadStatusChoiceBox();
        loadStudySpecificChoiceBox();
    }

    /**
     * Handles clicking of the List Rooms button.
     */
    public void retrieveAllRooms() {
        roomResult.clear();
        List<Room> rooms;
        try {
            rooms = new ArrayList<>(Objects.requireNonNull(JsonMapper.roomListMapper(ServerCommunication.getRooms())));
        } catch (Exception e) {
            // Fakes the table having any entries, so the table shows up properly instead of "No contents".
            rooms = new ArrayList<>();
            rooms.add(null);
        }

        totalPages = Math.ceil(rooms.size() / 15.0);

        if (totalPages < pageNumber) {
            pageNumber--;
        }

        pagesText.setText(pageNumber + " / " + (int) totalPages + " pages");

        if (rooms.size() > 16) {
            for (int i = 1; i < 16; i++) {
                try {
                    roomResult.add(rooms.get((i - 15) + pageNumber * 15));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            roomResult.addAll(rooms);
        }
        table.setItems(roomResult);
    }

    /**
     * Switches scene to DatabaseAddRooms.fxml
     *
     * @throws IOException Input will be valid
     */
    public void goToAddRooms() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseAddRooms.fxml");
    }

    /**
     * returns to the main menu
     *
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */
    public void mainMenu() throws IOException {
        ApplicationDisplay.changeScene("/DatabaseMainMenu.fxml");
    }

    /**
     * Handles the clicking to the next table page.
     */
    public void nextPage() {
        if (pageNumber < (int) totalPages) {
            pageNumber++;
            retrieveAllRooms();
        }
    }

    /**
     * Handles the clicking to the previous page
     */
    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        retrieveAllRooms();
    }

    /**
     * Takes care of finding a room by name or ID.
     */
    public void findRoom() {
        try {
            int id = Integer.parseInt(idFieldRead.getText());
            Room room = JsonMapper.roomMapper(ServerCommunication.findRoom(id));
            if (room != null) {
                roomResult.clear();
                roomResult.add(room);
                table.setItems(roomResult);
                pagesText.setText("1 / 1 pages");
            }
        } catch (Exception e) {
            String name = idFieldRead.getText();
            Room room = JsonMapper.roomMapper(ServerCommunication.findRoomByName(name));
            if (room != null) {
                roomResult.clear();
                roomResult.add(room);
                table.setItems(roomResult);
                pagesText.setText("1 / 1 pages");
            }
        }
    }

    /**
     * Updates a room directly from the fields.
     */
    public void updateRoom() {
        int id;
        boolean passes = true;
        try {
            id = Integer.parseInt(idFieldRead.getText());
            Room room = JsonMapper.roomMapper(ServerCommunication.findRoom(id));
            assert room != null;
            if (!room.getName().equals(nameFieldRead.getText())) {
                String response = ServerCommunication.updateRoom(id, "name", nameFieldRead.getText());
                if (response.equals("Name already exists.")) {
                    passes = false;
                    CustomAlert.warningAlert("Name already exists.");
                }
            }
            if (!room.getStudySpecific().equals(studyChoiceBox.getValue())) {
                ServerCommunication.updateRoom(id, "studyspecific", studyChoiceBox.getValue());
            }
            if (!(room.getStatus().equals(statusChoiceBox.getValue()))) {
                ServerCommunication.updateRoom(id, "status", statusChoiceBox.getValue());
            }
            if (!(room.hasProjector() == Boolean.parseBoolean(projectorToggle.getText()))) {
                ServerCommunication.updateRoom(id, "projector", projectorToggle.getText().toLowerCase());
            }
            if (!(room.hasScreen() == Boolean.parseBoolean(screenToggle.getText()))) {
                ServerCommunication.updateRoom(id, "screen", screenToggle.getText().toLowerCase());
            }
            try {
                if (!(room.getCapacity() == Integer.parseInt(capacityReadField.getText()))) {
                    ServerCommunication.updateRoom(id, "capacity", capacityReadField.getText());
                }
            } catch (NumberFormatException e) {
                CustomAlert.warningAlert("Capacity has to be an integer.");
                passes = false;
            }
            try {
                if (!(room.getPlugs() == Integer.parseInt(plugsReadField.getText()))) {
                    ServerCommunication.updateRoom(id, "plugs", plugsReadField.getText());
                }
            } catch (NumberFormatException e) {
                CustomAlert.warningAlert("Plugs has to be an integer.");
                passes = false;
            }
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
            passes = false;
        }
        if (passes) {
            CustomAlert.informationAlert("Successfully Executed.");
        } else {
            CustomAlert.warningAlert("Executed with warnings.");
        }
        retrieveAllRooms();
    }

    /**
     * Handles clicking of the Remove Room button.
     */
    public void deleteRoom() {
        try {
            int id = Integer.parseInt(idFieldRead.getText());
            ServerCommunication.deleteRoom(id);
            roomResult.removeIf(r -> r.getId() == id);
        } catch (Exception e) {
            CustomAlert.warningAlert("No selection detected.");
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickProjector() {
        if (projectorToggleFlag) {
            projectorToggle.setText("False");
            projectorToggleFlag = false;
        } else {
            projectorToggle.setText("True");
            projectorToggleFlag = true;
        }
    }

    /**
     * Makes sure the button toggles from false to true every time.
     */
    @FXML
    private void toggleClickScreen() {
        if (screenToggleFlag) {
            screenToggle.setText("false");
            screenToggleFlag = false;
        } else {
            screenToggle.setText("true");
            screenToggleFlag = true;
        }
    }

    /**
     * Listener that checks if a row is selected, if so, fill the text fields.
     */
    public void tableSelectMethod() {
        table.getSelectionModel().selectedItemProperty().addListener((obs) -> {
            anchorPane.getChildren().remove(deleteButton);

            final Room room = table.getSelectionModel().getSelectedItem();
            if (room != null) {
                idFieldRead.setText(Integer.toString(room.getId()));
                nameFieldRead.setText(room.getName());
                buildingFieldRead.setText(room.getBuilding().getName());
                studyChoiceBox.setValue(room.getStudySpecific());
                statusChoiceBox.setValue(room.getStatus());
                screenToggle.setText(Boolean.toString(room.hasScreen()));
                plugsReadField.setText(Integer.toString(room.getPlugs()));
                capacityReadField.setText(Integer.toString(room.getCapacity()));
                if (!screenToggleFlag == room.hasScreen()) {
                    screenToggleFlag = !screenToggleFlag;
                    screenToggle.setSelected(screenToggleFlag);
                }
                projectorToggle.setText(Boolean.toString(room.hasProjector()));
                if (!projectorToggleFlag == room.hasProjector()) {
                    projectorToggleFlag = !projectorToggleFlag;
                    projectorToggle.setSelected(projectorToggleFlag);
                }
            }

            for (int i = 0; i < roomResult.size(); i++) {
                assert room != null;
                if (roomResult.get(i).getId().equals(room.getId())) {
                    deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(1120);
                    deleteButton.setLayoutY(168 + (24 * (i + 1)));
                    deleteButton.setMinWidth(60);
                    deleteButton.setStyle("-fx-background-color:  #CC5653; -fx-font-size:10; -fx-text-fill: white");
                    deleteButton.setMinHeight(20);
                    deleteButton.setOnAction(event -> {
                        for (int i1 = 0; i1 < roomResult.size(); i1++) {
                            if (roomResult.get(i1).getId().equals(room.getId())) {
                                roomResult.remove(roomResult.get(i1));
                                anchorPane.getChildren().remove(deleteButton);
                            }
                        }
                        String response = ServerCommunication.deleteRoom(room.getId());
                        retrieveAllRooms();
                        CustomAlert.informationAlert(response);
                    });
                    anchorPane.getChildren().add(deleteButton);
                }
            }
        });
    }

    private void loadStudySpecificChoiceBox() {
        studyList.clear();
        String a = "";
        String b = "Computer Science and Engineering";
        studyList.addAll(a, b);
        studyChoiceBox.getItems().addAll(studyList);
    }

    private void loadStatusChoiceBox() {
        statusList.clear();
        String a = "Open";
        String b = "Closed";
        String c = "Staff-Only";
        String d = "Maintenance";
        statusList.addAll(a, b, c, d);
        statusChoiceBox.getItems().addAll(statusList);
    }
}
