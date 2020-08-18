package dataEntry;

import javafx.beans.property.SimpleStringProperty;

/**
 * Customer class
 */
public class Customers {

    public SimpleStringProperty customerID, customerName, customerContact, model, registrationNo, chassisNo, registrationDate;

    /**
     * Corporate customer constructor
     * @param customerID unique identity
     * @param customerName name of customer
     * @param customerContact phone number of customer
     * @param model model of their car
     * @param registrationNo registration number of their car
     * @param registrationDate registration date of their car
     * @param chassisNo chassis number of their car
     */
    public Customers(String customerID, String customerName, String customerContact, String model, String registrationNo, String chassisNo, String registrationDate) {
        this.customerID = new SimpleStringProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerContact = new SimpleStringProperty(customerContact);
        this.model = new SimpleStringProperty(model);
        this.registrationNo = new SimpleStringProperty(registrationNo);
        this.chassisNo = new SimpleStringProperty(chassisNo);
        this.registrationDate = new SimpleStringProperty(registrationDate);
    }

    public Customers() {
    }

    /**
     * Setters and Getters that will get overridden in the specific user class. Polymorphism yay
     * @return
     */
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

    public String getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(String customerID) {
        this.customerID.set(customerID);
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

    public String getChassisNo() {
        return chassisNo.get();
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo.set(chassisNo);
    }

    public String getRegistrationDate() {
        return registrationDate.get();
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate.set(registrationDate);
    }
}
