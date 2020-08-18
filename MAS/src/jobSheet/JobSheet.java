package jobSheet;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Job sheet class for all job sheet items
 */
public class JobSheet {

    private SimpleStringProperty serviceOrderNumber, customerID, serviceNotes, jobItems;

    /**
     *
     * @param serviceOrderNumber unique identifier for every new service order
     * @param customerID unique identifier for every customer for which the service order is made
     * @param serviceNotes additional notes that need to be passed to the mechanic
     * @param jobItems actual jobs assigned to the mechanic
     */
    public JobSheet(String serviceOrderNumber, String customerID, String serviceNotes, String jobItems) {
        this.serviceOrderNumber = new SimpleStringProperty(serviceOrderNumber);
        this.customerID = new SimpleStringProperty(customerID);
        this.serviceNotes = new SimpleStringProperty(serviceNotes);
        this.jobItems = new SimpleStringProperty(jobItems);
    }

    /**
     * Used to scan the job sheet DB and add the job sheets to an observable list
     * @return Observable list of all job sheets
     * @throws IOException
     */
    public static ObservableList<JobSheet> readingDB() throws IOException {

        ObservableList<JobSheet> sheet = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/jobSheet/jobSheetDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            sheet.add(new JobSheet(scan.next(), scan.next(), scan.next(), scan.next()));
        }
        scan.close();

        return sheet;
    }

    /**
     * Getters and Setters for all job sheet items
     * @return
     */
    public String getServiceOrderNumber() {
        return serviceOrderNumber.get();
    }

    public void setServiceOrderNumber(String serviceOrderNumber) {
        this.serviceOrderNumber.set(serviceOrderNumber);
    }

    public String getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(String customerID) {
        this.customerID.set(customerID);
    }

    public String getServiceNotes() {
        return serviceNotes.get();
    }

    public void setServiceNotes(String serviceNotes) {
        this.serviceNotes.set(serviceNotes);
    }

    public String getJobItems() {
        return jobItems.get();
    }

    public void setJobItems(String jobItems) {
        this.jobItems.set(jobItems);
    }

}
