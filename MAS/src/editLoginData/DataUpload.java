package editLoginData;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataUpload implements Initializable {

    @FXML
    public TableView<Users> staffTable;
    @FXML
    private TableColumn<Users, String> permissionColumn;
    @FXML
    private TableColumn<Users, String> userIDColumn;
    @FXML
    private TableColumn<Users, String> passwordColumn;
    @FXML
    private TextField permissionTextField;
    @FXML
    private TextField userIDTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label message;
    @FXML
    private Label error;


    /** The following code blocks help to change info on the GUI tableview
     * @param editedCell: The selected permission cell on the table for which you want to pass in new data
     */
    public void changePermission(TableColumn.CellEditEvent editedCell) {
        Users staffSelected = staffTable.getSelectionModel().getSelectedItem();
        staffSelected.setPermission(editedCell.getNewValue().toString());
    }

    /**
     * @param editedCell: The selected user ID cell on the table for which you want to pass in new data
     */
    public void changeUserID(TableColumn.CellEditEvent editedCell) {
        Users staffSelected = staffTable.getSelectionModel().getSelectedItem();
        staffSelected.setUserID(editedCell.getNewValue().toString());
    }

    /**
     * @param editedCell: The selected password cell on the table for which you want to pass in new data
     */
    public void changePassword(TableColumn.CellEditEvent editedCell) {
        Users staffSelected = staffTable.getSelectionModel().getSelectedItem();
        staffSelected.setPassword(editedCell.getNewValue().toString());
    }


    /**
     * Removes selected staff from the DB
     * @throws IOException
     */
    public void deleteButtonPushed() throws IOException {

        ObservableList<Users> selectedRows, allPeople;
        allPeople = staffTable.getItems();
        selectedRows = staffTable.getSelectionModel().getSelectedItems();

        for (Users staff : selectedRows) {
            allPeople.remove(staff);
            savingDB(System.getProperty("user.dir") + "/src/login/staffDB.txt");
        }

    }

    /**
     * Adds a new staff member into the DB
     * @throws IOException
     */
    public void newPersonButtonPushed() throws IOException {

        if(permCheck()){

            if (nullCheck()) {
                message.setText("");
                error.setText("");
                Users newUser = new Users(
                        permissionTextField.getText(),
                        userIDTextField.getText(),
                        passwordTextField.getText()
                );
                staffTable.getItems().add(newUser);
                savingDB(System.getProperty("user.dir") + "/src/login/staffDB.txt");
                permissionTextField.setText("");
                userIDTextField.setText("");
                passwordTextField.setText("");
            } else {
                error.setText("Please fill all fields");
            }

        } else {
            error.setText("Permission should be one of the following: 001, 002, 003 or 004");
        }


    }

    /**
     * Updates the current staff into the DB
     * @throws IOException
     */
    public void saveButtonPushed() throws IOException {

        savingDB(System.getProperty("user.dir") + "/src/login/staffDB.txt");
        error.setText("");
        message.setText("Data Updated!");
    }

    /**
     * Saves the staff into the Users observable list
     * @param filename source of the database
     * @throws IOException
     */
    public void savingDB(String filename) throws IOException {

        ObservableList<Users> allPeople = staffTable.getItems();

        FileWriter writer = new FileWriter(filename);
        for (Users saveUser : allPeople) {
            writer.write(saveUser.getPermission() + "," + saveUser.getUserID() + "," + saveUser.getPassword() + "\n");
        }
        writer.close();
    }

    /**
     * Checks to see if fields are left empty
     * @return boolean value for whether the form is valid
     */
    public boolean nullCheck() {

        boolean valid = true;

        if (permissionTextField.getText().isEmpty() || userIDTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            valid = false;
        }
        return valid;
    }

    /**
     * Permission should only takes specific values and this determines that validity
     * @return validPerm as true if one of specified permissions is chosen
     */
    public boolean permCheck(){
        boolean validPerm = false;

        if(permissionTextField.getText().equals("001")||
                permissionTextField.getText().equals("002")||
                permissionTextField.getText().equals("003")||
                permissionTextField.getText().equals("004")){
            validPerm = true;
        }
        return validPerm;
    }

    /**
     * Changes to the required scene
     * @param event button push event for back
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("systemAdministrator/systemAdministrator.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    /**
     * Helps to set the scene with the relevant info such as populating the tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting cell values
        permissionColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("permission"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("userID"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("password"));

        try {
            //Setting the table users after reading the DB
            staffTable.setItems(Users.readingDB());
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

        // Letting table values be editable using GUI
        staffTable.setEditable(true);
        permissionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Allowing multiple selects in order to delete items
        staffTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

}
