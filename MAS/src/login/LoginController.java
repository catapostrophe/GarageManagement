package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {

    public static String permission;
    private static Scanner scan;

    @FXML
    public TextField staffid;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;

    @FXML
    void submit(ActionEvent event) {

        // Null checkers
        if (staffid.getText().isEmpty() && password.getText().isEmpty()) {
            error.setText("Please enter your login credentials");
        } else if (staffid.getText().isEmpty()) {
            error.setText("Please enter your Staff ID");
        } else if (password.getText().isEmpty()) {
            error.setText("Please enter your password");
        } else {
            error.setText("");
            verifyLogin(staffid.getText(), password.getText(), System.getProperty("user.dir") + "/src/login/staffDB.txt", event);
        }

    }

    /**
     * @param user:     the staff ID (user) is scanned from the GUI input
     * @param pass:     the password is scanned from the GUI input
     * @param filepath: the txt file where the database of users and their passwords are hardcoded
     */
    public void verifyLogin(String user, String pass, String filepath, ActionEvent event) {

        boolean found = false;
        String userID;
        String password;

        try {

            scan = new Scanner(new File(filepath));
            // Separates txt file values based on new lines and commas
            scan.useDelimiter("[,\n]");

            while (scan.hasNext() && !found) {

                permission = scan.next();
                userID = scan.next();
                password = scan.next();

                // Trims to avoid any possible whitespace
                if (userID.trim().equals(user.trim()) && password.trim().equals(pass.trim())) {
                    found = true;

                    // Sends the staff to the relevant scene depending on their permission level
                    if (permission.equals("001")) {
                        sceneChange(event, "dataEntry/dataEntryDashboard.fxml");
                    } else if (permission.equals("002")) {
                        sceneChange(event, "jobSheet/serviceAdvisorDashboard.fxml");
                    } else if (permission.equals("003")) {
                        sceneChange(event, "jobSheet/jobSheetTable.fxml");
                    } else if (permission.equals("004")) {
                        sceneChange(event, "systemAdministrator/systemAdministrator.fxml");
                    }

                } else {
                    error.setText("Your password and / or username is incorrect");
                }
            }

            scan.close();

        } catch (Exception e) {

            System.out.println("Error:" + e.getLocalizedMessage());

        }

    }

    /**
     * Helps to switch between scenes easily
     * @param event relevant button push event
     * @param fxmlFile string of the relevant fxml file to change scenes to
     * @throws IOException
     */
    public void sceneChange(ActionEvent event, String fxmlFile) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFile));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }


}