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

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

public class JobItemController implements Initializable {

    @FXML
    public TableView<JobItem> jobTable;
    public int number = 1;
    @FXML
    private TableColumn<JobItem, String> jobItemColumn;
    @FXML
    private TextField jobTextField;
    @FXML
    private TextArea serviceNotes;
    @FXML
    private Label serviceOrderNumber;
    @FXML
    private Label message;
    @FXML
    private Label error;
    @FXML
    private TextField customerNo;

    /**
     * @param editedCell: The selected permission cell on the table for which you want to pass in new data
     */
    public void changeJobCell(TableColumn.CellEditEvent editedCell) {
        JobItem jobSelected = jobTable.getSelectionModel().getSelectedItem();
        jobSelected.setJob(editedCell.getNewValue().toString());
    }

    /**
     * Adds new jobs into a DB
     * @throws IOException
     */
    public void addNewJob() throws IOException {

        if (jobTextField.getText().isEmpty()) {
            error.setText("Please make sure job item field is filled");
        } else {
            error.setText("");

            JobItem newJob = new JobItem(
                    jobTextField.getText());
            jobTable.getItems().add(newJob);
            savingItem(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt");
            jobTextField.setText("");
        }
    }

    /**
     * Deletes jobs from the DB
     * @throws IOException
     */
    public void deleteSavedJob() throws IOException {
        ObservableList<JobItem> selectedRows, allJobs;
        allJobs = jobTable.getItems();
        selectedRows = jobTable.getSelectionModel().getSelectedItems();

        for (JobItem job : selectedRows) {
            allJobs.remove(job);
            savingItem(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt");
        }
    }

    /**
     * Saves the DB with the current jobs
     * @throws IOException
     */
    public void saveJobSheet() throws IOException {

            if (customerNo.getText().isEmpty() || serviceNotes.getText().isEmpty()) {
                error.setText("Please make sure all the fields are filled");
            } else {
                error.setText("");
                // Makes sure all changes are tracked
                savingItem(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt");
                // Saves the whole sheet with all current values into the database
                savingSheet(System.getProperty("user.dir") + "/src/jobSheet/jobSheetDB.txt");
                // Clears the file so that redundant values won't appear for the next SO
                clearingDB(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt");
                message.setText("Job Sheet Saved!");
            }

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
     * Saves both the jobs and also a new job sheet that has the information filled in by the Service Advisor
     * @param filename source of the txt file to which we want the final information about the job sheet written
     * @throws IOException
     */
    public void savingSheet(String filename) throws IOException {

        String setAllJobItems = null;

        if(fileCheck(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt")){
            String allJobItems = Files.readString(Paths.get(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt"), StandardCharsets.US_ASCII);
            setAllJobItems = allJobItems.substring(0, allJobItems.length() - 3);
        } else {
            setAllJobItems = "No job items recorded";
        }

        FileWriter noWriter = new FileWriter(System.getProperty("user.dir") + "/src/jobSheet/serviceOrderDB.txt");
        noWriter.write(number + "\n");

        noWriter.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.write((generateServiceOrderNo("%04d", "SO/2020/")) + ","
                + customerNo.getText() + ","
                + serviceNotes.getText() + ","
                + setAllJobItems + "\n");

        writer.close();

    }

    /**
     * Appending all the job items as a single string value so it can easily be written and read later
     * @param filename
     * @throws IOException
     */
    public void savingItem(String filename) throws IOException {

        ObservableList<JobItem> allJobs = jobTable.getItems();

        FileWriter writer = new FileWriter(filename);
        for (JobItem updateJob : allJobs) {
            writer.write((updateJob.getJob() + " & "));//changed
        }
        writer.close();

    }

    /**
     * Creates blank files so that new job sheets won't have old data
     * @param filename
     * @throws IOException
     */
    public void clearingDB(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write("");
        writer.close();
    }

    /**
     * Generates a unique service order number for every job sheet
     * @param decimals number of decimal points before the actual SO number
     * @param start the beginning of the string for the service order
     * @return the full string for the service order which can be set into labels
     * @throws IOException
     */
    public String generateServiceOrderNo(String decimals, String start) throws IOException {
        String soNumber = null;
        String formatted = String.format(decimals, number);
        soNumber = start + formatted;

        return soNumber;
    }

    /**
     * Helps to set the scene with the relevant info such as the service order number
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            number = startSO() + 1;
            serviceOrderNumber.setText(generateServiceOrderNo("%04d", "SO/2020/"));

            // Setting cell values
            jobItemColumn.setCellValueFactory(new PropertyValueFactory<JobItem, String>("job"));

            //tableView.setItems(Users.readingDB());
            jobTable.setItems(JobItem.readingDB());

            // Letting table values be editable using GUI
            jobTable.setEditable(true);
            jobItemColumn.setCellFactory(TextFieldTableCell.forTableColumn());

            // Allowing multiple selects in order to delete items
            jobTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * Checks the number of the last service order created
     * @return number of the last service order saved
     * @throws FileNotFoundException
     */
    public int startSO() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/jobSheet/serviceOrderDB.txt"));
        scan.useDelimiter("[,\n]");
        while (scan.hasNext()) {
            number = (Integer.parseInt(scan.next()));
        }
        scan.close();
        return number;
    }

    /**
     * Changes to the required scene
     * @param event button push event for back
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        // Clears unsaved items
        clearingDB(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt");
        // Changes scene
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("jobSheet/jobSheetTable.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

}
