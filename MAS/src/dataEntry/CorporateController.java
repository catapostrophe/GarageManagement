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

public class CorporateController implements Initializable {

    @FXML
    public TableView<Corporate> corporateTable;
    @FXML
    private TableColumn<Corporate, String> customerIDColumn;
    @FXML
    private TableColumn<Corporate, String> customerNameColumn;
    @FXML
    private TableColumn<Corporate, String> contactNoColumn;
    @FXML
    private TableColumn<Corporate, String> companyNameColumn;
    @FXML
    private TableColumn<Corporate, String> modelColumn;
    @FXML
    private TableColumn<Corporate, String> regNoColumn;
    @FXML
    private TableColumn<Corporate, String> regDateColumn;
    @FXML
    private TableColumn<Corporate, String> chassisColumn;
    @FXML
    private TableColumn<Corporate, String> inspectionDateColumn;
    @FXML
    private TableColumn<Corporate, String> statusColumn;
    @FXML
    private TableColumn<Corporate, String> remarksColumn;
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
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCustomerID(editedCell.getNewValue().toString());
    }

    public void changeCustomerName(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCustomerName(editedCell.getNewValue().toString());
    }

    public void changeCustomerContact(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCustomerContact(editedCell.getNewValue().toString());
    }

    public void changeCompanyName(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setCompanyName(editedCell.getNewValue().toString());
    }

    public void changeCustomerModel(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setModel(editedCell.getNewValue().toString());
    }

    public void changeCustomerRegNo(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setRegistrationNo(editedCell.getNewValue().toString());
    }

    public void changeCustomerRegDate(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setRegistrationDate(editedCell.getNewValue().toString());
    }

    public void changeChassis(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setChassisNo(editedCell.getNewValue().toString());
    }

    public void changeInspectionDate(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setInspectionDate(editedCell.getNewValue().toString());
    }

    public void changeStatus(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setStatus(editedCell.getNewValue().toString());
    }

    public void changeRemarks(TableColumn.CellEditEvent editedCell) {
        Corporate customerSelected = corporateTable.getSelectionModel().getSelectedItem();
        customerSelected.setRemarks(editedCell.getNewValue().toString());
    }

    /**
     * Deletes selected customer from the table
     * @throws IOException
     */
    public void deleteButtonPushed() throws IOException {
        ObservableList<Corporate> selectedRows, allPeople;
        allPeople = corporateTable.getItems();
        selectedRows = corporateTable.getSelectionModel().getSelectedItems();

        for (Corporate person : selectedRows) {
            allPeople.remove(person);
            savingDB(System.getProperty("user.dir") + "/src/dataEntry/corporateCustomersDB.txt");
        }
    }

    /**
     * Saves the current table along with any changes
     * @throws IOException
     */
    public void saveButtonPushed() throws IOException {
        savingDB(System.getProperty("user.dir") + "/src/dataEntry/corporateCustomersDB.txt");
        message.setText("Data Updated!");
    }

    /**
     * Saves the customer information into a txt file
     * @param filename source of the file to which we save the data
     * @throws IOException
     */
    public void savingDB(String filename) throws IOException {

        ObservableList<Corporate> allPeople = corporateTable.getItems();

        FileWriter writer = new FileWriter(filename);
        for (Corporate saveCustomer : allPeople) {
            writer.write(saveCustomer.getCustomerID() + ","
                    + saveCustomer.getCustomerName() + ","
                    + saveCustomer.getCustomerContact() + ","
                    + saveCustomer.getCompanyName() + ","
                    + saveCustomer.getModel() + ","
                    + saveCustomer.getRegistrationNo() + ","
                    + saveCustomer.getRegistrationDate() + ","
                    + saveCustomer.getChassisNo() + ","
                    + saveCustomer.getInspectionDate() + ","
                    + saveCustomer.getStatus() + ","
                    + saveCustomer.getRemarks() + "\n");
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
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("customerName"));
        contactNoColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("customerContact"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("companyName"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("model"));
        regNoColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("registrationNo"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("registrationDate"));
        chassisColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("chassisNo"));
        inspectionDateColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("inspectionDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("status"));
        remarksColumn.setCellValueFactory(new PropertyValueFactory<Corporate, String>("remarks"));

        try {
            // Setting the cell values for the TableView
            corporateTable.setItems(Corporate.readingDB());
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

        // Letting table values be editable using GUI
        corporateTable.setEditable(true);
        customerIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactNoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        companyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        regNoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        regDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        chassisColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        inspectionDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        remarksColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Allowing multiple selects in order to delete items
        corporateTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
}
