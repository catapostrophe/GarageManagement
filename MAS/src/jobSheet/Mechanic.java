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
import javafx.util.converter.IntegerStringConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Mechanic implements Initializable {

    @FXML public TableView<SpareParts> spareTable;
    @FXML public TableView<LabourItem> labourTable;
    @FXML private TableColumn<SpareParts, Integer> quantityColumn;
    @FXML private TableColumn<SpareParts, String> spareNumberColumn;
    @FXML private TableColumn<SpareParts, String> spareNameColumn;
    @FXML private TableColumn<SpareParts, Integer> priceColumn;
    @FXML private TableColumn<SpareParts, Integer> discountColumn;
    @FXML private TextField quantityTextField;
    @FXML private TextField numberTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField discountTextField;
    @FXML private TableColumn<LabourItem, String> labourColumn;
    @FXML private TableColumn<LabourItem, Integer> timeColumn;
    @FXML private TextField labourTextField;
    @FXML private TextField timeTextField;

    @FXML private TextField mechName;
    @FXML private TextField chargerate;
    @FXML private TextField mechID;
    @FXML private TextField soNumber;

    @FXML private Label message;
    @FXML private Label error;
    @FXML private Button saveButton;

    /**
     * The following code blocks help to change the values in the GUI tables
     * @param editedCell changed cell
     */
    public void changeQuantity(TableColumn.CellEditEvent editedCell) {
        SpareParts staffSelected = spareTable.getSelectionModel().getSelectedItem();
        staffSelected.setQuantity((Integer) editedCell.getNewValue());
    }

    public void changeNumber(TableColumn.CellEditEvent editedCell) {
        SpareParts staffSelected = spareTable.getSelectionModel().getSelectedItem();
        staffSelected.setSpareNumber(editedCell.getNewValue().toString());
    }

    public void changeName(TableColumn.CellEditEvent editedCell) {
        SpareParts staffSelected = spareTable.getSelectionModel().getSelectedItem();
        staffSelected.setSpareName(editedCell.getNewValue().toString());
    }

    public void changePrice(TableColumn.CellEditEvent editedCell) {
        SpareParts staffSelected = spareTable.getSelectionModel().getSelectedItem();
        staffSelected.setPrice((Integer) editedCell.getNewValue());
    }

    public void changeDiscount(TableColumn.CellEditEvent editedCell) {
        SpareParts staffSelected = spareTable.getSelectionModel().getSelectedItem();
        staffSelected.setDiscount((Integer) editedCell.getNewValue());
    }

    public void changeLabour(TableColumn.CellEditEvent editedCell) {
        LabourItem staffSelected = labourTable.getSelectionModel().getSelectedItem();
        staffSelected.setLabour(editedCell.getNewValue().toString());
    }

    public void changeTime(TableColumn.CellEditEvent editedCell) {
        LabourItem staffSelected = labourTable.getSelectionModel().getSelectedItem();
        staffSelected.setTime((Integer) editedCell.getNewValue());
    }

    /**
     * Deletes spare from the DB
     * @throws IOException
     */
    public void deleteSpare() throws IOException {
        ObservableList<SpareParts> selectedRows, allSpares;
        allSpares = spareTable.getItems();
        selectedRows = spareTable.getSelectionModel().getSelectedItems();

        for (SpareParts spares : selectedRows) {
            allSpares.remove(spares);
            savingSpareItem();
        }
    }

    /**
     * Deletes labour from the DB
     * @throws IOException
     */
    public void deleteLabour() throws IOException {
        ObservableList<LabourItem> selectedRows, allLabour;
        allLabour = labourTable.getItems();
        selectedRows = labourTable.getSelectionModel().getSelectedItems();

        for (LabourItem labour : selectedRows) {
            allLabour.remove(labour);
            savingLabourItem();
        }
    }

    /**
     * Adds spare into the DB with null checkers and resets the text fields
     * @throws IOException
     */
    public void logSpare() throws IOException {

        // Validity check for alpha characters in num fields
        if(checkSpareNum()){

            // Validity check for empty fields
            if (quantityTextField.getText().isEmpty() ||
                    numberTextField.getText().isEmpty() ||
                    nameTextField.getText().isEmpty() ||
                    priceTextField.getText().isEmpty() ||
                    discountTextField.getText().isEmpty()) {
                error.setText("Please make sure all spare log fields are filled");
            } else {
                error.setText("");
                SpareParts newSpare = new SpareParts(
                        Integer.parseInt(quantityTextField.getText()),
                        numberTextField.getText(),
                        nameTextField.getText(),
                        Integer.parseInt(priceTextField.getText()),
                        Integer.parseInt(discountTextField.getText())
                );
                spareTable.getItems().add(newSpare);
                savingSpareItem();

                quantityTextField.setText("");
                numberTextField.setText("");
                nameTextField.setText("");
                priceTextField.setText("");
                discountTextField.setText("");
            }

        } else {
            error.setText("Please use only numbers for the fields: quantity, price and discount. Discount should only be up to 100");
        }

    }

    /**
     * Adds labour into the DB with null checkers and resets the text fields
     * @throws IOException
     */
    public void logLabour() throws IOException {

        // Validity check for alpha characters in num fields
        if(checkLabourNum()) {

            // Validity check for empty fields
            if (labourTextField.getText().isEmpty() || timeTextField.getText().isEmpty()) {
                error.setText("Please make sure all labour log fields are filled");
            } else {
                error.setText("");
                LabourItem newLabour = new LabourItem(
                        labourTextField.getText(),
                        Integer.parseInt(timeTextField.getText())
                );
                labourTable.getItems().add(newLabour);
                savingLabourItem();

                labourTextField.setText("");
                timeTextField.setText("");
            }
        } else {
            error.setText("Please use only numbers for time field");
        }

    }

    /**
     * Saves the current sheet with 0 to n spares and labour items and a log of the mechanic that attended the job
     * @throws IOException
     */
    public void saveButtonPushed() throws IOException {

        if (mechName.getText().isEmpty() ||
                mechID.getText().isEmpty() ||
                chargerate.getText().isEmpty() ||
                soNumber.getText().isEmpty()) {
            error.setText("Please make sure all mechanic detail fields are filled");
        } else {

            error.setText("");

            // In case any changes were made to the spare items
            savingSpareItem();
            savingLabourItem();

            // Saves the entire log
            savingSheet();

            // Once data has been saved if we try to rewrite it can cause problems so if you want you can disable after one log
            //saveButton.setDisable(true);

            clearingDB(System.getProperty("user.dir") + "/src/jobSheet/spareDB.txt");
            clearingDB(System.getProperty("user.dir") + "/src/jobSheet/labourDB.txt");
            message.setText("Data Updated!");
        }
    }

    /**
     * Saves all spare items into a sheet as a single string so it can be read and written easily
     * @throws IOException
     */
    public void savingSpareItem() throws IOException {

        ObservableList<SpareParts> allSpares = spareTable.getItems();

        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/src/jobSheet/spareDB.txt");
        for (SpareParts updateSpare : allSpares) {
            writer.write((updateSpare.getQuantity() + "  "
                    + updateSpare.getSpareNumber() + "  "
                    + updateSpare.getSpareName() + "  "
                    + updateSpare.getPrice() + "  "
                    + updateSpare.getDiscount() + " & "));
        }
        writer.close();
    }

    /**
     * Saves all labour items into a sheet as a single string so it can be read and written easily
     * @throws IOException
     */
    public void savingLabourItem() throws IOException {

        ObservableList<LabourItem> allLabour = labourTable.getItems();

        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/src/jobSheet/labourDB.txt");
        for (LabourItem updateLabour : allLabour) {
            writer.write((updateLabour.getLabour() + "  "
                    + updateLabour.getTime() + " & "));
        }
        writer.close();
    }

    /**
     * Saving the entire mechanic log to a databsse
     * @throws IOException
     */
    public void savingSheet() throws IOException {

        String setAllSpares = null;
        String setAllLabour = null;

        // Null checkers for validity
        if (fileCheck(System.getProperty("user.dir") + "/src/jobSheet/spareDB.txt")) {
            // Reads a whole line in the current logged spares DB as a string
            String allSpares = Files.readString(Paths.get(System.getProperty("user.dir") + "/src/jobSheet/spareDB.txt"), StandardCharsets.US_ASCII);
            // Saves it without the final appending item so that it displays in a logical manner without an extra delimiter
            setAllSpares = allSpares.substring(0, allSpares.length() - 3);
        } else {
            // If there are no lines in the text file (meaning no spares were used) the database gets a string saying no spares added
            setAllSpares = "No Spares";
        }

        // Null checkers for validity
        if (fileCheck(System.getProperty("user.dir") + "/src/jobSheet/labourDB.txt")) {
            // Reads a whole line in the current logged labour DB as a string
            String allLabour = Files.readString(Paths.get(System.getProperty("user.dir") + "/src/jobSheet/labourDB.txt"), StandardCharsets.US_ASCII);
            // Saves it without the final appending item so that it displays in a logical manner without an extra delimiter
            setAllLabour = allLabour.substring(0, allLabour.length() - 3);
        } else {
            // If there are no lines in the text file (meaning no labour was logged) the database gets a string saying no labour added
            setAllLabour = "No Labour";
        }

        // Writing the collected data into a mechanic log database
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/src/jobSheet/mechanicLogDB.txt", true);

        writer.write((soNumber.getText() + "  "
                + mechName.getText() + "  "
                + mechID.getText() + "  "
                + chargerate.getText() + "  "
                + setAllSpares + "  "
                + setAllLabour + "\n"));

        writer.close();
    }

    /**
     * Checks to see if there is no text in a given file
     * @param filepath source of the DB
     * @return notNull as true if there is content, false if its blank
     * @throws IOException
     */
    public boolean fileCheck(String filepath) throws IOException {
        boolean notNull = true;
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        if (br.readLine() == null) {
            notNull = false;
        }
        return notNull;
    }

    /**
     * Clears the file so that old data will not be shown when a new log needs to be made
     * @param filename
     * @throws IOException
     */
    public void clearingDB(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write("");
        writer.close();
    }

    /**
     * Helps to set the scene with the relevant info such as the initial tableview items
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting cell values
        quantityColumn.setCellValueFactory(new PropertyValueFactory<SpareParts, Integer>("quantity"));
        spareNumberColumn.setCellValueFactory(new PropertyValueFactory<SpareParts, String>("spareNumber"));
        spareNameColumn.setCellValueFactory(new PropertyValueFactory<SpareParts, String>("spareName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<SpareParts, Integer>("price"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<SpareParts, Integer>("discount"));
        labourColumn.setCellValueFactory(new PropertyValueFactory<LabourItem, String>("labour"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<LabourItem, Integer>("time"));

        try {
            spareTable.setItems(SpareParts.readingDB());
            labourTable.setItems(LabourItem.readingDB());
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

        // Letting table values be editable using GUI
        spareTable.setEditable(true);
        labourTable.setEditable(true);
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn((new IntegerStringConverter())));
        spareNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        spareNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn((new IntegerStringConverter())));
        discountColumn.setCellFactory(TextFieldTableCell.forTableColumn((new IntegerStringConverter())));
        labourColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn((new IntegerStringConverter())));

        // Allowing multiple selects in order to delete items
        spareTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        labourTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /**
     * Check to see if a alpha character was entered into fields where we need to get doubles extracted
     * @return spareNum as true if the required fields do have numbers
     */
    public boolean checkSpareNum(){
        boolean spareNum = false;

        if(quantityTextField.getText().matches("^[0-9]{1,5}$")&&priceTextField.getText().matches("^[0-9]{1,5}$")&&discountTextField.getText().matches("^[0-9]{1,3}$")){
            spareNum = true;
        }
        return spareNum;
    }

    /**
     * Check to see if a alpha character was entered into fields where we need to get doubles extracted
     * @return labourNum as true if the required field does have a number
     */
    public boolean checkLabourNum(){
        boolean labourNum = false;

        if(timeTextField.getText().matches("^[0-9]{1,5}$")){
            labourNum = true;
        }
        return labourNum;
    }

    /**
     * Scene change to the relevant scene
     * @param event button push event to go to the previous scene
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        // In case of dumped logs that were not saved
        clearingDB(System.getProperty("user.dir") + "/src/jobSheet/spareDB.txt");
        clearingDB(System.getProperty("user.dir") + "/src/jobSheet/labourDB.txt");

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("jobSheet/jobSheetTable.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }


}
