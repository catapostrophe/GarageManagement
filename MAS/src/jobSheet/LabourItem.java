package jobSheet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Labour items class
 */
public class LabourItem {

    private SimpleStringProperty labour;
    private SimpleIntegerProperty time;

    /**
     *
     * @param labour the work done
     * @param time the minutes logged
     */
    public LabourItem(String labour, int time) {
        this.labour = new SimpleStringProperty(labour);
        this.time = new SimpleIntegerProperty(time);
    }

    /**
     * Used to scan the labour DB and add the items to an observable list
     * @return Observable list of all labour
     * @throws IOException
     */
    public static ObservableList<LabourItem> readingDB() throws IOException {

        ObservableList<LabourItem> work = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/jobSheet/labourDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            work.add(new LabourItem(scan.next(), Integer.parseInt(scan.next())));
        }
        scan.close();

        return work;
    }

    /**
     * Setters and Getters for all labour items
     * @return
     */
    public String getLabour() {
        return labour.get();
    }

    public void setLabour(String labour) {
        this.labour.set(labour);
    }

    public int getTime() {
        return time.get();
    }

    public void setTime(int time) {
        this.time.set(time);
    }


}
