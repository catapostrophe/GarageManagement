package dataEntry;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Corporate customers class
 */
public class Corporate extends Customers {

    private SimpleStringProperty customerID, customerName, customerContact, companyName, model, registrationNo, registrationDate, chassisNo, inspectionDate, status, remarks;

    /**
     * Corporate customer constructor
     * @param customerID unique identity
     * @param customerName name of customer
     * @param customerContact phone number of customer
     * @param companyName company to which they belong
     * @param model model of their car
     * @param registrationNo registration number of their car
     * @param registrationDate registration date of their car
     * @param chassisNo chassis number of their car
     * @param inspectionDate inspection date of their car
     * @param status status of their car
     * @param remarks remarks about their car
     */
    public Corporate(String customerID,
                     String customerName,
                     String customerContact,
                     String companyName,
                     String model,
                     String registrationNo,
                     String registrationDate,
                     String chassisNo,
                     String inspectionDate,
                     String status,
                     String remarks) {

        this.customerID = new SimpleStringProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerContact = new SimpleStringProperty(customerContact);
        this.companyName = new SimpleStringProperty(companyName);
        this.model = new SimpleStringProperty(model);
        this.registrationNo = new SimpleStringProperty(registrationNo);
        this.registrationDate = new SimpleStringProperty(registrationDate);
        this.chassisNo = new SimpleStringProperty(chassisNo);
        this.inspectionDate = new SimpleStringProperty(inspectionDate);
        this.status = new SimpleStringProperty(status);
        this.remarks = new SimpleStringProperty(remarks);
    }

    /**
     * Database reader for corporate customers
     * @return list of corporate customers in the form of an observable list
     * @throws IOException
     */
    public static ObservableList<Corporate> readingDB() throws IOException {

        ObservableList<Corporate> corporateList = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/dataEntry/corporateCustomersDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            corporateList.add(new Corporate(scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next(),
                    scan.next()));
        }
        scan.close();

        return corporateList;
    }

    /**
     * Getters and setters for the corporate customers
     */
    public String getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(String customerID) {
        this.customerID.set(customerID);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getCustomerContact() {
        return customerContact.get();
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact.set(customerContact);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getRegistrationNo() {
        return registrationNo.get();
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo.set(registrationNo);
    }

    public String getRegistrationDate() {
        return registrationDate.get();
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate.set(registrationDate);
    }

    public String getChassisNo() {
        return chassisNo.get();
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo.set(chassisNo);
    }

    public String getInspectionDate() {
        return inspectionDate.get();
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate.set(inspectionDate);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

}
