package systemAdministrator;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SystemAdministrator {

    /**
     * Helps the system administrator access the edit login data scene to view/change/add/delete system users
     * @param event button push to head to a new scene
     * @throws IOException
     */
    @FXML
    void editData(ActionEvent event) throws IOException {
        sceneChanger(event, "editLoginData/editLoginData.fxml");
    }

    /**
     * Helps the system administrator access the twitter search or feed display
     * @param event button push to head to a new scene
     * @throws IOException
     */
    @FXML
    void twitter(ActionEvent event) throws IOException {
        sceneChanger(event, "twitter/twitter.fxml");
    }

    /**
     * Helps the system administrator logout
     * @param event button push to head to a new scene
     * @throws IOException
     */
    @FXML
    void logout(ActionEvent event) throws IOException {
        sceneChanger(event, "login/login.fxml");
    }

    /**
     * Helps to switch between scenes easily
     * @param event:    Button push event
     * @param fxmlFile: The fxml file of the scene you want to load
     * @throws IOException: Because an fxml file is loaded
     */
    public void sceneChanger(ActionEvent event, String fxmlFile) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFile));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

}
