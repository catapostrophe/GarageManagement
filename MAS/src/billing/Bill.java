package billing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Bill implements Initializable {

    private static double discountedSpare;
    private static double labourCost;
    private static double spareCost;
    private static double subtotal;
    private static double gst;
    public int number = 1;
    public String billDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    @FXML private Label hrMainLabel;
    @FXML private Label mechIDLabel;
    @FXML private Label mechNameLabel;
    @FXML private Label tMainLabel;
    @FXML private Label soLabel;
    @FXML private Label billNoLabel;
    @FXML private Label todayLabel;
    @FXML private Label spareNumberLabel;
    @FXML private Label spareNameLabel;
    @FXML private Label sqLabel;
    @FXML private Label dLabel;
    @FXML private Label spLabel;
    @FXML private Label discountedSpareLabel;
    @FXML private Label tLabel;
    @FXML private Label labourLabel;
    @FXML private Label hrLabel;
    @FXML private Label labourCostLabel;
    @FXML private Label finalSpareLabel;
    @FXML private Label finalLabourLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label gstLabel;
    @FXML private Label totalLabel;

    @FXML private Label custCodeLabel;
    @FXML private Label custNameLabel;
    @FXML private Label custContactLabel;
    @FXML private Label vehicleLabel;
    @FXML private Label regNoLabel;
    @FXML private Label regDateLabel;
    @FXML private Label chassisLabel;

    @FXML private Label message;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setMechFields();
        setCustFields();
        System.out.println();

    }

    /**
     * Saves the current invoice number every time the back button is pushed. This number is stored in billNoDB.txt
     * @throws IOException
     */
    public void saveBillNo() throws IOException {
        FileWriter noWriter = new FileWriter(System.getProperty("user.dir") + "/src/billing/billNoDB.txt");
        noWriter.write(number + "\n");

        noWriter.close();
    }

    /**
     * Generates a new invoice number every time generate bill is pushed
     * @param decimals number of decimals to come before the actual number
     * @param start start pattern of the bill number
     * @return the full bill number
     * @throws IOException
     */
    public String generateBillNo(String decimals, String start) throws IOException {
        String billNumber = null;
        String formatted = String.format(decimals, number);
        billNumber = start + formatted;

        return billNumber;
    }

    /**
     * Finds the last bill number saved so that the newly generated bill can be based on this number
     * @return last bill number
     * @throws FileNotFoundException
     */
    public int startBillNo() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/billing/billNoDB.txt"));
        scan.useDelimiter("[,\n]");
        while (scan.hasNext()) {
            number = (Integer.parseInt(scan.next()));
        }
        scan.close();
        return number;
    }

    /**
     * Calculates the bill total using the values obtained when the mechanic saves his log
     * @param hr price for hourly rate
     * @param t number of minutes logged
     * @param sq quantity of spare
     * @param sp price of spare
     * @param d discount percentage
     * @return total bill amount
     */
    public static double calculateBill(double hr, double t, double sq, double sp, double d) {

        labourCost = (hr / 60) * t;
        spareCost = sq * sp;
        discountedSpare = (spareCost - (spareCost * d * 0.01));
        subtotal = labourCost + discountedSpare;
        gst = subtotal * 0.06;

        return subtotal + gst;
    }

    /**
     * Sets the labels for the mechanic/price fields on the prototype bill
     */
    public void setMechFields(){

        try {

            number = startBillNo() + 1;
            billNoLabel.setText(generateBillNo("%04d", "INV/2020/")); // Sets bill number
            todayLabel.setText(billDate); // Sets bill date as current date

            String serviceOrder = null,
                    mechName = null,
                    mechID = null,
                    hourlyRate = null,
                    spareQuantity = null,
                    spareNumber = null,
                    spareName = null,
                    sparePrice = null,
                    discount = null,
                    labour = null,
                    time = null;

            Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/jobSheet/mechanicLogDB.txt"));

            scan.useDelimiter("  ");

            while (scan.hasNext()) {
                serviceOrder = scan.next();
                mechName = scan.next();
                mechID = scan.next();
                hourlyRate = scan.next();
                spareQuantity = scan.next();
                spareNumber = scan.next();
                spareName = scan.next();
                sparePrice = scan.next();
                discount = scan.next();
                labour = scan.next();
                time = scan.next();

                double hr = Double.parseDouble(hourlyRate);
                double sq = Double.parseDouble(spareQuantity);
                double sp = Double.parseDouble(sparePrice);
                double d = Double.parseDouble(discount);
                double t = Double.parseDouble(time);

                calculateBill(hr, t, sq, sp, d);

                hrMainLabel.setText((String.format("%.2f", hr)) + " MVR");
                mechIDLabel.setText(mechID);
                mechNameLabel.setText(mechName);
                tMainLabel.setText((Math.round(t)) + " Minutes");
                soLabel.setText(serviceOrder);
                spareNumberLabel.setText(spareNumber);
                spareNameLabel.setText(spareName);
                sqLabel.setText(String.valueOf(Math.round(sq)));
                dLabel.setText((Math.round(d)) + "%");
                spLabel.setText((String.format("%.2f", spareCost)) + " MVR");
                discountedSpareLabel.setText((String.format("%.2f", discountedSpare)) + " MVR");
                tLabel.setText(String.valueOf((Math.round(t))));
                labourLabel.setText(labour);
                hrLabel.setText((String.format("%.2f", hr)) + " MVR");
                labourCostLabel.setText((String.format("%.2f", labourCost)) + " MVR");
                finalSpareLabel.setText((String.format("%.2f", discountedSpare)) + " MVR");
                finalLabourLabel.setText((String.format("%.2f", labourCost)) + " MVR");
                subtotalLabel.setText((String.format("%.2f", subtotal)) + " MVR");
                gstLabel.setText((String.format("%.2f", gst)) + " MVR");
                totalLabel.setText((String.format("%.2f", calculateBill(hr, t, sq, sp, d))) + " MVR");

            }

            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the labels for the customer fields on the prototype bill
     */
    public void setCustFields(){

        try {

            String custCode = null,
                    custName = null,
                    custContact = null,
                    custID = null,
                    vehicle = null,
                    regNo = null,
                    regDate = null,
                    chassis = null;

        Scanner scan = null;

            scan = new Scanner(new File(System.getProperty("user.dir") + "/src/dataEntry/privateCustomersDB.txt"));


        scan.useDelimiter(",");

            while (scan.hasNext()) {
                custCode = scan.next();
                custName = scan.next();
                custContact = scan.next();
                custID = scan.next();
                vehicle = scan.next();
                regNo = scan.next();
                regDate = scan.next();
                chassis = scan.next();

                custCodeLabel.setText(custCode);
                custNameLabel.setText(custName);
                custContactLabel.setText(custContact);
                vehicleLabel.setText(vehicle);
                regNoLabel.setText(regNo);
                regDateLabel.setText(regDate);
                chassisLabel.setText(chassis);

            }

            scan.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets print message for prototype bill
     */
    @FXML
    void print() {
        message.setText("Printed successfully");
    }


    /**
     * Goes back to the Job Sheet table view
     * @param event back button pushed
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {

        saveBillNo();

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("jobSheet/jobSheetTable.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

}
