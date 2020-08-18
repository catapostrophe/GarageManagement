package jobSheet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Service advisor dashboard
 */
public class ServiceAdvisor {

    /**
     * Helps service advisor access the job sheet scene to log new job sheets
     * @param event button push to head to a new scene
     * @throws IOException
     */
    @FXML
    void jobSheets(ActionEvent event) throws IOException {
        sceneChanger(event, "jobSheet/jobSheetTable.fxml");
    }

    /**
     * Helps service advisor view all registered private customers
     * @param event button push to head to a new scene
     * @throws IOException
     */
    @FXML
    void viewPrivateCustomers(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/privateTable.fxml");
    }

    /**
     * Helps service advisor view all registered corporate customers
     * @param event button push to head to a new scene
     * @throws IOException
     */
    @FXML
    void viewCorporateCustomers(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/corporateTable.fxml");
    }

    /**
     * Helps service advisor logout
     * @param event button push to head to login screen
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
