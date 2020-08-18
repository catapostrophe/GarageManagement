package jobSheet;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class for job items
 */
public class JobItem {

    private SimpleStringProperty job;

    /**
     *
     * @param job a job applied to the job sheet
     */
    public JobItem(String job) {
        this.job = new SimpleStringProperty(job);
    }

    /**
     * Used to scan the job DB and add the jobs to an observable list
     * @return Observable list of all jobs
     * @throws IOException
     */
    public static ObservableList<JobItem> readingDB() throws IOException {

        ObservableList<JobItem> job = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/jobSheet/jobsDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            job.add(new JobItem(scan.next()));
        }
        scan.close();

        return job; // people to staff Person to Users
    }

    /**
     * Setters and Getters for job objects
     * @return
     */
    public String getJob() {
        return job.get();
    }

    public void setJob(String job) {
        this.job.set(job);
    }
}
