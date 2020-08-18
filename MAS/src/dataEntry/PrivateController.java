package dataEntry;

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

public class PrivateController implements Initializable {

    @FXML
    public TableView<Private> privateTable;
    @FXML
    private TableColumn<Private, String> customerIDColumn;
    @FXML
    private TableColumn<Private, String> customerNameColumn;
    @FXML
    private TableColumn<Private, String> contactNoColumn;
    @FXML
    private TableColumn<Private, String> identityCardNoColumn;
    @FXML
    private TableColumn<Private, String> modelColumn;
    @FXML
    private TableColumn<Private, String> regNoColumn;
    @FXML
    private TableColumn<Private, String> regDateColumn;
    @FXML
    private TableColumn<Private, String> chassisColumn;
    @FXML
    private Label message;

    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private Button delete;

    /**
     * The following code blocks help to change the values in the GUI tables
     * @param editedCell changed cell
     */
    public void changeCustomerID(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCustomerID(editedCell.getNewValue().toString());
    }

    public void changeCustomerName(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCustomerName(editedCell.getNewValue().toString());
    }

    public void changeCustomerContact(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCustomerContact(editedCell.getNewValue().toString());
    }

    public void changeCustomerIdentityCardNo(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setIdentityCardNo(editedCell.getNewValue().toString());
    }

    public void changeCustomerModel(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setModel(editedCell.getNewValue().toString());
    }

    public void changeCustomerRegNo(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setRegistrationNo(editedCell.getNewValue().toString());
    }

    public void changeCustomerRegDate(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setRegistrationDate(editedCell.getNewValue().toString());
    }

    public void changeChassis(TableColumn.CellEditEvent editedCell) {
        Private customerSelected = privateTable.getSelectionModel().getSelectedItem();
        customerSelected.setChassisNo(editedCell.getNewValue().toString());
    }

    /**
     * Deletes selected customer from the table
     * @throws IOException
     */
    public void deleteButtonPushed() throws IOException {
        ObservableList<Private> selectedRows, allPeople;
        allPeople = privateTable.getItems();
        selectedRows = privateTable.getSelectionModel().getSelectedItems();

        for (Private person : selectedRows) {
            allPeople.remove(person);
            savingDB(System.getProperty("user.dir") + "/src/dataEntry/privateCustomersDB.txt");
        }
    }

    /**
     * Saves the current table along with any changes
     * @throws IOException
     */
    public void saveButtonPushed() throws IOException {
        savingDB(System.getProperty("user.dir") + "/src/dataEntry/privateCustomersDB.txt");
        message.setText("Data Updated!");
    }

    /**
     * Saves the customer information into a txt file
     * @param filename source of the file to which we save the data
     * @throws IOException
     */
    public void savingDB(String filename) throws IOException {

        ObservableList<Private> allPeople = privateTable.getItems();

        FileWriter writer = new FileWriter(filename);
        for (Private saveCustomer : allPeople) {
            writer.write(saveCustomer.getCustomerID() + ","
                    + saveCustomer.getCustomerName() + ","
                    + saveCustomer.getCustomerContact() + ","
                    + saveCustomer.getIdentityCardNo() + ","
                    + saveCustomer.getModel() + ","
                    + saveCustomer.getRegistrationNo() + ","
                    + saveCustomer.getRegistrationDate() + ","
                    + saveCustomer.getChassisNo() + "\n");
        }
        writer.close();
    }

    /**
     * Changes to the required scene
     * @param event button push event for adding
     * @throws IOException
     */
    @FXML
    void addNew(ActionEvent event) throws IOException {
        sceneChanger(event, "dataEntry/dataEntry.fxml");
    }

    /**
     * Changes to the required scene
     * @param event button push event for back
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        if ((login.LoginController.permission).equals("001")) {
            sceneChanger(event, "dataEntry/dataEntryDashboard.fxml");
        } else if ((login.LoginController.permission).equals("002")) {
            sceneChanger(event, "jobSheet/serviceAdvisorDashboard.fxml");
        }
    }

    /**
     * Changes to the required scene
     * @param event button push event for logout
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

    /**
     * Helps to set the scene with the relevant info such as proper buttons depending on permission level
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Service advisors aren't allowed to add customers and so won't see those buttons
        if ((login.LoginController.permission).equals("002")) {
            add.setVisible(false);
            edit.setVisible(false);
            delete.setVisible(false);
        }

        // Setting cell values
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("customerName"));
        contactNoColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("customerContact"));
        identityCardNoColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("identityCardNo"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("model"));
        regNoColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("registrationNo"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("registrationDate"));
        chassisColumn.setCellValueFactory(new PropertyValueFactory<Private, String>("chassisNo"));

        try {
            // Setting the cell values for the TableView
            privateTable.setItems(Private.readingDB());
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

        // Letting table values be editable using GUI
        privateTable.setEditable(true);
        customerIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactNoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        identityCardNoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        regNoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        regDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        chassisColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Allowing multiple selects in order to delete items
        privateTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

}
