package jobSheet;

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

public class JobSheetController implements Initializable {

    @FXML
    public TableView<JobSheet> jobSheetTable;
    @FXML
    public TableColumn<JobSheet, String> serviceOrderNumberColumn;
    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Button print;
    @FXML
    private Button bill;
    @FXML
    private Button logmech;
    @FXML
    private Button back;
    @FXML
    private TableColumn<JobSheet, String> customerIDColumn;
    @FXML
    private TableColumn<JobSheet, String> serviceNotesColumn;
    @FXML
    private TableColumn<JobSheet, String> jobItemsColumn;
    @FXML
    private Label message;

    /**
     * The following code blocks help to change the values in the GUI tables
     * @param editedCell changed cell
     */
    public void changeServiceOrderNumber(TableColumn.CellEditEvent editedCell) {
        JobSheet sheetSelected = jobSheetTable.getSelectionModel().getSelectedItem();
        sheetSelected.setServiceOrderNumber(editedCell.getNewValue().toString());
    }

    public void changeCustomerID(TableColumn.CellEditEvent editedCell) {
        JobSheet sheetSelected = jobSheetTable.getSelectionModel().getSelectedItem();
        sheetSelected.setCustomerID(editedCell.getNewValue().toString());
    }

    public void changeServiceNotes(TableColumn.CellEditEvent editedCell) {
        JobSheet sheetSelected = jobSheetTable.getSelectionModel().getSelectedItem();
        sheetSelected.setServiceNotes(editedCell.getNewValue().toString());
    }

    public void changeJobItems(TableColumn.CellEditEvent editedCell) {
        JobSheet sheetSelected = jobSheetTable.getSelectionModel().getSelectedItem();
        sheetSelected.setJobItems(editedCell.getNewValue().toString());
    }

    /**
     * Deletes job sheets from the DB
     * @throws IOException
     */
    public void deleteButtonPushed() throws IOException {
        message.setText("");
        ObservableList<JobSheet> selectedRows, allJobs;
        allJobs = jobSheetTable.getItems();
        selectedRows = jobSheetTable.getSelectionModel().getSelectedItems();

        for (JobSheet sheets : selectedRows) {
            allJobs.remove(sheets);
            savingDB(System.getProperty("user.dir") + "/src/jobSheet/jobSheetDB.txt");
        }

    }

    /**
     * Fake prints, save the environment people!
     */
    @FXML
    void print() {
        message.setText("Printed Successfully");
    }

    /**
     * Saves the current job sheet into a DB
     * @throws IOException
     */
    public void saveButtonPushed() throws IOException {
        savingDB(System.getProperty("user.dir") + "/src/jobSheet/jobSheetDB.txt");
        message.setText("Data Updated!");
    }

    /**
     * Writes all the items in the job sheet observable list into the job sheet database
     * @param filename source of the txt file where job sheets are stored
     * @throws IOException
     */
    public void savingDB(String filename) throws IOException {

        ObservableList<JobSheet> allJobs = jobSheetTable.getItems();
        FileWriter writer = new FileWriter(filename);
        for (JobSheet saveSheet : allJobs) {
            writer.write(saveSheet.getServiceOrderNumber() + "," + saveSheet.getCustomerID() + "," + saveSheet.getServiceNotes() + "," + saveSheet.getJobItems() + "\n");
        }
        writer.close();
    }

    /**
     * Scene change to the relevant scene
     * @param event button push event to go to the scene to add a new job
     */
    @FXML
    void addNewJobSheet(ActionEvent event) {
        try {
            sceneChanger(event, "jobSheet/jobSheetSA.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scene change to the relevant scene
     * @param event button push event to go to the scene to log mechanic
     */
    @FXML
    void logMechanic(ActionEvent event) throws IOException {
        sceneChanger(event, "jobSheet/jobSheetM.fxml");
    }

    /**
     * Scene change to the relevant scene
     * @param event button push event to go to the bill scene
     */
    @FXML
    void generateBill(ActionEvent event) throws IOException {
        sceneChanger(event, "billing/billing.fxml");
    }

    /**
     * Scene change to the relevant scene
     * @param event button push event to go logout
     */
    @FXML
    void logout(ActionEvent event) throws IOException {
        sceneChanger(event, "login/login.fxml");
    }

    /**
     * Scene change to the relevant scene
     * @param event button push event to go to the previous scene
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        sceneChanger(event, "jobSheet/serviceAdvisorDashboard.fxml");
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
     * Helps to set the scene with the relevant info such as the correct buttons depending the permission level of the logged in staff
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets service advisors and mechanics to see buttons that are relevant to them on the same screen
        if ((login.LoginController.permission).equals("002")) {
            logmech.setVisible(false);
        } else if ((login.LoginController.permission).equals("003")) {
            add.setVisible(false);
            edit.setVisible(false);
            delete.setVisible(false);
            bill.setVisible(false);
            print.setVisible(false);
            back.setVisible(false);
            message.setVisible(false);
        }


        // Setting cell values
        serviceOrderNumberColumn.setCellValueFactory(new PropertyValueFactory<JobSheet, String>("serviceOrderNumber"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<JobSheet, String>("customerID"));
        serviceNotesColumn.setCellValueFactory(new PropertyValueFactory<JobSheet, String>("serviceNotes"));
        jobItemsColumn.setCellValueFactory(new PropertyValueFactory<JobSheet, String>("jobItems"));

        try {
            // Populating the job sheet TableView
            jobSheetTable.setItems(JobSheet.readingDB());
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

        // Letting table values be editable using GUI
        jobSheetTable.setEditable(true);
        serviceOrderNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        customerIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serviceNotesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        jobItemsColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Allowing multiple selects in order to delete items
        jobSheetTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
