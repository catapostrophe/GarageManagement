package editLoginData;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class for User Objects
 */
public class Users {

    private SimpleStringProperty permission, userID, password;

    /**
     *
     * @param permission permission level that each staff has
     * @param userID username with which they can login
     * @param password password with which they can login
     */
    public Users(String permission, String userID, String password) {
        this.permission = new SimpleStringProperty(permission);
        this.userID = new SimpleStringProperty(userID);
        this.password = new SimpleStringProperty(password);
    }

    /**
     * Getters and Setters for the staff
     * @return
     */
    public String getPermission() {
        return permission.get();
    }

    public void setPermission(String permission) {
        this.permission.set(permission);
    }

    public String getUserID() {
        return userID.get();
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    /**
     * Used to scan the staff DB and add the members to an observable list
     * @return Observable list of all staff members
     * @throws IOException
     */
    public static ObservableList<Users> readingDB() throws IOException {

        ObservableList<Users> staff = FXCollections.observableArrayList();

        Scanner scan = new Scanner(new File(System.getProperty("user.dir") + "/src/login/staffDB.txt"));
        scan.useDelimiter("[,\n]");

        while (scan.hasNext()) {

            staff.add(new Users(scan.next(), scan.next(), scan.next()));
        }
        scan.close();

        return staff; // people to staff Person to Users
    }


}
