package dataEntry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DataEntryStaff {


    /**
     * Changes to the relevant scene
     * @param event button push event for adding new customer
     * @throws IOException
     */
    @FXML
    void addNew(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/dataEntry.fxml");
    }

    /**
     * Changes to the relevant scene
     * @param event button push event to view all registered private customers
     * @throws IOException
     */
    @FXML
    void viewPrivateCustomers(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/privateTable.fxml");
    }

    /**
     * Changes to the relevant scene
     * @param event button push event to view all registered corporate customers
     * @throws IOException
     */
    @FXML
    void viewCorporateCustomers(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/corporateTable.fxml");
    }

    /**
     * Changes to the relevant scene
     * @param event button push event to logout
     * @throws IOException
     */
    @FXML
    void logout(ActionEvent event) throws IOException {
        sceneChanger(event, "login/login.fxml");
    }

    /**
     * Helps to switch between scenes easily
     * @param event relevant button push event
     * @param fxmlFile string of the relevant fxml file to change scenes to
     * @throws IOException
     */
    public void sceneChanger(ActionEvent event, String fxmlFile) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFile));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

}
