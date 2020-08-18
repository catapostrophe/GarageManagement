package jobSheet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Spare item class
 */
public class SpareParts {

    private SimpleStringProperty spareNumber, spareName;
    private SimpleIntegerProperty quantity, price, discount;

    /**
     *
     * @param quantity quantity of the specific spare item used
     * @param spareNumber unique identifier for the spare
     * @param spareName name of the spare
     * @param price price of the spare
     * @param discount discount applied to the spare part
     */
    public SpareParts(int quantity, String spareNumber, String spareName, int price, int discount) {
        this.quantity = new SimpleIntegerProperty(quantity);
        this.spareNumber = new SimpleStringProperty(spareNumber);
        this.spareName = new SimpleStringProperty(spareName);
        this.price = new SimpleIntegerProperty(price);
        this.discount = new SimpleIntegerProperty(discount);
    }

    /**
     * Used to scan the spare DB and add the items to an observable list
     * @return Observable list of all spare items
     * @throws IOException
     */
    public static ObservableList<SpareParts> readingDB() throws IOException {

        ObservableList<SpareParts> partLog = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/jobSheet/spareDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            partLog.add(new SpareParts(Integer.parseInt(scan.next()), scan.next(), scan.next(), Integer.parseInt(scan.next()), Integer.parseInt(scan.next())));
        }
        scan.close();

        return partLog;
    }

    /**
     * Setters and Getters for all spare items
     * @return
     */
    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getSpareNumber() {
        return spareNumber.get();
    }

    public void setSpareNumber(String spareNumber) {
        this.spareNumber.set(spareNumber);
    }

    public String getSpareName() {
        return spareName.get();
    }

    public void setSpareName(String spareName) {
        this.spareName.set(spareName);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getDiscount() {
        return discount.get();
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }


}
