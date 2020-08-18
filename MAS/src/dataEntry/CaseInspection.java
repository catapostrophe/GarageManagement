package dataEntry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CaseInspection {

    public String customerID = null;
    public int corpcustomerIDNo = 1;
    public int privcustomerIDNo = 1;
    @FXML private TextField model;
    @FXML private TextField regNo;
    @FXML private TextField chassis;
    @FXML private TextField regDate;
    @FXML private TextField customerName;
    @FXML private TextField customerContact;
    @FXML private TextField idcardorcompany;
    @FXML private Label iclabel;
    @FXML private RadioButton privateCustomer;
    @FXML private RadioButton corporateCustomer;
    @FXML private GridPane corporateGrid;
    @FXML private TextField status;
    @FXML private TextField inspectionDate;
    @FXML private TextArea remarks;
    @FXML private Label currentID;
    @FXML private Label message;
    @FXML private Label error;

    /**
     * Settings to allow different boxes to be visible for corporate customers since they require more info
     * @throws FileNotFoundException
     */
    @FXML
    void corporateSelected() throws FileNotFoundException {
        if (corporateCustomer.isSelected()) {
            iclabel.setText("Company Name");
            corporateGrid.setVisible(true);
            corpcustomerIDNo = (trackCustomerNo(System.getProperty("user.dir") + "/src/dataEntry/corpNo.txt") + 1);
            customerID = "C000" + corpcustomerIDNo;
            currentID.setText(customerID);
        }
    }

    /**
     * Settings to allow different boxes to be visible for private customers since they require less info
     * @throws FileNotFoundException
     */
    @FXML
    void privateSelected() throws FileNotFoundException {
        if (privateCustomer.isSelected()) {
            iclabel.setText("ID Card Number");
            corporateGrid.setVisible(false);
            privcustomerIDNo = (trackCustomerNo(System.getProperty("user.dir") + "/src/dataEntry/privNo.txt") + 1);
            customerID = "P000" + privcustomerIDNo;
            currentID.setText(customerID);
        }
    }

    /**
     * Registers a new customer. Different settings are applied to the customer types.
     * @throws IOException
     */
    @FXML
    void registerCustomer() throws IOException {

        if (corporateCustomer.isSelected()) {

            if (model.getText().isEmpty() ||
                    regNo.getText().isEmpty() ||
                    chassis.getText().isEmpty() ||
                    regDate.getText().isEmpty() ||
                    customerName.getText().isEmpty() ||
                    customerContact.getText().isEmpty() ||
                    idcardorcompany.getText().isEmpty() ||
                    status.getText().isEmpty() ||
                    inspectionDate.getText().isEmpty() ||
                    remarks.getText().isEmpty()) {

                message.setText("");
                error.setText("Please make sure all fields are filled");

            } else {
                // Writing into database
                saveToDB(System.getProperty("user.dir") + "/src/dataEntry/corporateCustomersDB.txt");
                saveCorporateCustomerNo(System.getProperty("user.dir") + "/src/dataEntry/corpNo.txt");

                error.setText("");
                message.setText("Registered Successfully!");
                corporateCustomer.setSelected(false);
                clearFields();
            }


        } else if (privateCustomer.isSelected()) {

            if (model.getText().isEmpty() ||
                    regNo.getText().isEmpty() ||
                    chassis.getText().isEmpty() ||
                    regDate.getText().isEmpty() ||
                    customerName.getText().isEmpty() ||
                    customerContact.getText().isEmpty() ||
                    idcardorcompany.getText().isEmpty()) {

                message.setText("");
                error.setText("Please make sure all fields are filled");

            } else {

                saveToDB(System.getProperty("user.dir") + "/src/dataEntry/privateCustomersDB.txt");
                savePrivateCustomerNo(System.getProperty("user.dir") + "/src/dataEntry/privNo.txt");

                error.setText("");
                message.setText("Registered Successfully!");
                privateCustomer.setSelected(false);
                clearFields();
            }

        } else if (!corporateCustomer.isSelected() && !privateCustomer.isSelected()) {

            message.setText("");
            error.setText("Please make sure customer type is selected");

        }

    }

    /**
     *
     * @param event button push event on logout button
     * @throws IOException
     */
    @FXML
    void logout(ActionEvent event) throws IOException {
        sceneChanger(event, "login/login.fxml");
    }

    /**
     *
     * @param event button push event on back button
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/dataEntryDashboard.fxml");
    }

    /**
     * Resets all text fields
     */
    public void clearFields() {
        model.setText("");
        regNo.setText("");
        chassis.setText("");
        regDate.setText("");
        customerName.setText("");
        customerContact.setText("");
        idcardorcompany.setText("");
        status.setText("");
        inspectionDate.setText("");
        remarks.setText("");

    }

    /**
     * Writes all the information about customers into a txt database
     * @param filepath source path of the file we want to write to
     */
    public void saveToDB(String filepath) {

        try {

            FileWriter writer = new FileWriter(filepath, true);

            if (status.getText().isEmpty() && inspectionDate.getText().isEmpty() && remarks.getText().isEmpty()) {
                writer.write(customerID + ","
                        + customerName.getText() + ","
                        + customerContact.getText() + ","
                        + idcardorcompany.getText() + ","
                        + model.getText() + ","
                        + regNo.getText() + ","
                        + regDate.getText() + ","
                        + chassis.getText() + "\n");
            } else {
                writer.write(customerID + ","
                        + customerName.getText() + ","
                        + customerContact.getText() + ","
                        + idcardorcompany.getText() + ","
                        + model.getText() + ","
                        + regNo.getText() + ","
                        + regDate.getText() + ","
                        + chassis.getText() + ","
                        + inspectionDate.getText() + ","
                        + status.getText() + ","
                        + remarks.getText() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tracks customer number so that each customer gets a unique number
     * @param filepath source of the txt file form which we get the most recently issued customer number
     * @return ID of the last customer upon which the new customer number can be set
     * @throws FileNotFoundException
     */
    public int trackCustomerNo(String filepath) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filepath));
        scan.useDelimiter("[,\n]");
        int number = 0;
        while (scan.hasNext()) {
            number = (Integer.parseInt(scan.next()));
        }
        scan.close();
        return number;
    }

    /**
     * General function to switch from scene to scene
     * @param event any button push event
     * @param fxmlFile
     * @throws IOException
     */
    public void sceneChanger(ActionEvent event, String fxmlFile) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFile));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    /**
     * Function to save the ID number for private customers
     * @param filename source of the txt file to which we write in the number
     * @throws IOException
     */
    public void savePrivateCustomerNo(String filename) throws IOException {
        FileWriter noWriter = new FileWriter(filename);
        noWriter.write(privcustomerIDNo + "\n");
        noWriter.close();
    }

    /**
     * Function to save the ID number for corporate customers
     * @param filename source of the txt file to which we write in the number
     * @throws IOException
     */
    public void saveCorporateCustomerNo(String filename) throws IOException {
        FileWriter noWriter = new FileWriter(filename);
        noWriter.write(corpcustomerIDNo + "\n");
        noWriter.close();
    }

}
