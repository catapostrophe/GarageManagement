package dataEntry;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Private customers class
 */
public class Private extends Customers {

    private SimpleStringProperty customerID, customerName, customerContact, identityCardNo, model, registrationNo, registrationDate, chassisNo;

    /**
     * Private customer constructor
     * @param customerID unique identity
     * @param customerName name of customer
     * @param customerContact phone number of customer
     * @param identityCardNo personal identification number
     * @param model model of their car
     * @param registrationNo registration number of their car
     * @param registrationDate registration date of their car
     * @param chassisNo chassis number of their car
     */
    public Private(String customerID,
                   String customerName,
                   String customerContact,
                   String identityCardNo,
                   String model,
                   String registrationNo,
                   String registrationDate,
                   String chassisNo) {

        this.customerID = new SimpleStringProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerContact = new SimpleStringProperty(customerContact);
        this.identityCardNo = new SimpleStringProperty(identityCardNo);
        this.model = new SimpleStringProperty(model);
        this.registrationNo = new SimpleStringProperty(registrationNo);
        this.registrationDate = new SimpleStringProperty(registrationDate);
        this.chassisNo = new SimpleStringProperty(chassisNo);

    }

    /**
     * Database reader for private customers
     * @return list of private customers in the form of an observable list
     * @throws IOException
     */
    public static ObservableList<Private> readingDB() throws IOException {

        ObservableList<Private> privateList = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/dataEntry/privateCustomersDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            privateList.add(new Private(scan.next(), scan.next(), scan.next(), scan.next(), scan.next(), scan.next(), scan.next(), scan.next()));
        }
        scan.close();

        return privateList;
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

    public String getIdentityCardNo() {
        return identityCardNo.get();
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo.set(identityCardNo);
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


}
