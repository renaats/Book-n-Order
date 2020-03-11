package nl.tudelft.oopp.demo.controllers;

public class MainMenuController {

    /**
     * Handels the clicking of the button under the bike image at the main menu.
     * @throws IOException when it fails
     */
    public void calendarIcon() throws IOException {
        ApplicationDisplay.changeScene("/calendar.fxml");
    }

    /**
     * Changes to bookRoom.fxml.
     * @throws IOException again, all input will be valid. No need to check this, thus we throw.
     */

    public void goToProfile() throws IOException {
        ApplicationDisplay.changeScene("/myAccountScene.fxml");
    }

    /**
     * Changes current scene to myCurrentBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */

    public void goToMainReservationsMenu() throws IOException {
        ApplicationDisplay.changeScene("/MainMenuReservations.fxml");
    }

    /**
     * testing method should be deleted
     * @param actionEvent
     * @throws IOException
     */
    public void template(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/Template.fxml");
    }

    public void calendarIcon(MouseEvent mouseEvent) {

    }

    /**
     * Changes to myCurrentRoomBookings.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentRoomBookings() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentRoomBookings.fxml");
    }

    /**
     * Changes to myCurrentFoodOrders.fxml.
     * @throws IOException input will not be wrong, hence we throw.
     */
    public void myCurrentFoodOrders() throws IOException {
        ApplicationDisplay.changeScene("/myCurrentFoodOrders.fxml");
    }

    public void template(ActionEvent actionEvent) throws IOException {
        ApplicationDisplay.changeScene("/Template.fxml");
    }

    public void calendarIcon(MouseEvent mouseEvent) {

    }
}
